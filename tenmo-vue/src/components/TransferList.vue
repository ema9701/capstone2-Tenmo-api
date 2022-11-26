<template>
  <div id="transfer-list">
    <v-expansion-panels
      v-for="transfer in transfers"
      :key="transfer.transferId"
    >
      <v-expansion-panel>
        <v-expansion-panel-title>
          <template v-slot:default="{ expanded }">
            <v-row no-gutters>
              <v-col cols="4" class="d-flex justify-start">
                Transfer ID No. {{ transfer.transferId }}
              </v-col>
              <v-col cols="8" class="text-grey">
                <v-fade-transition leave-absolute>
                  <span v-if="expanded" key="0"> Transfer Details</span>
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
                <td>{{ transfer.transferDate }}</td>
                <td>{{ transfer.accountFrom }}</td>
                <td>{{ transfer.accountTo }}</td>
                <td>${{ transfer.amount }}</td>
                <td>{{ transfer.status }}</td>
              </tr>
            </tbody>
          </v-table>
        </v-expansion-panel-text>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
</template>

<script>
import trxServices from "@/services/TransactionServices.js";

export default {
  data() {
    return {
      user: {},
      transfers: [],
      filter: {},
    };
  },
  created() {
    this.listTransfers(this.currentUserId);
  },
  methods: {
    listTransfers(userId) {
      trxServices.listTransfersByUserId(userId).then((response) => {
        this.transfers = response.data;
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

<style scoped></style>
