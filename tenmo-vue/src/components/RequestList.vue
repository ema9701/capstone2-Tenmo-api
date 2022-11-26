<template>
  <div id="request-list">
    <v-expansion-panels v-for="request in requests" :key="request.requestId">
      <v-expansion-panel>
        <v-expansion-panel-title>
          <template v-slot:default="{ expanded }">
            <v-row no-gutters>
              <v-col cols="4" class="d-flex justify-start">
                Request ID No. {{ request.requestId }}
              </v-col>
              <v-col cols="8" class="text-grey">
                <v-fade-transition leave-absolute>
                  <span v-if="expanded" key="0"> Request Details</span>
                </v-fade-transition>
              </v-col>
            </v-row>
          </template>
        </v-expansion-panel-title>
        <v-expansion-panel-text>
          <v-table>
            <thead>
              <tr>
                <th>Date:</th>
                <th>From account:</th>
                <th>To account:</th>
                <th>Amount:</th>
                <th>Status:</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{{ request.requestDate }}</td>
                <td>{{ request.requester }}</td>
                <td>{{ request.grantor }}</td>
                <td>${{ request.amount }}</td>
                <td>{{ request.status }}</td>
              </tr>
            </tbody>
          </v-table>
          <request-btn v-if="request.status === 'PENDING'">
            <template v-slot:statusSelect>
              <v-radio-group v-model="requestApproved" required>
                <v-radio label="Approve Request" :value="true"></v-radio>
                <v-radio label="Reject Request" :value="false"></v-radio>
              </v-radio-group>
            </template>
            <template v-slot:update>
              <v-btn
                color="secondary"
                @click="updateRequestStatus(request.requestId, requestApproved)"
                >Submit update</v-btn
              >
            </template>
          </request-btn>
        </v-expansion-panel-text>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
</template>

<script>
import trxServices from "@/services/TransactionServices";
import requestBtn from "@/components/RequestAuthBtn.vue";

export default {
  components: {
    requestBtn,
  },
  data() {
    return {
      user: {},
      requests: {},
      requestApproved: {},
    };
  },
  created() {
    this.listRequests(this.currentUserId);
  },
  methods: {
    listRequests(userId) {
      trxServices.listRequestsByUserId(userId).then((response) => {
        this.requests = response.data;
      });
    },
    updateRequestStatus(requestId, approve) {
      trxServices
        .updateStatus(requestId, approve)
        .then((response) => {
          if (response.status === 200) {
            alert("Request status has been updated!");
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  computed: {
    currentUserId() {
      return this.$store.state.user.id;
    },
  },
};
</script>

<style>
</style>