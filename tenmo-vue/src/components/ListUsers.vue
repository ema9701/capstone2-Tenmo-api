<template>
  <div>
    <user-card v-for="user in users" :key="user.id" :user="user">
      <template v-slot:submitForm>
        <v-form>
          <v-container>
            <v-row>
              <v-col cols="auto" md="6">
                <v-label v-model.number="transactionDTO.from"
                  >FROM: {{ currentUserId }}
                </v-label>
              </v-col>
              <v-col cols="auto" md="6">
                <v-label v-model.number="transactionDTO.to">
                  TO: {{ user.id }}
                </v-label>
              </v-col>
              <v-col cols="auto" md="6">
                <v-text-field
                  type="number"
                  v-model.number="transactionDTO.amount"
                  label="Amount"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="auto" md="6">
                <v-radio-group inline required v-model="transactionType">
                  <v-radio label="Transfer" :value="true"></v-radio>
                  <v-radio label="Request" :value="false"></v-radio>
                </v-radio-group>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </template>
      <template v-slot:sendTransfer>
        <v-btn
          color="secondary"
          @click.prevent="printDTO(user.id, transactionType)"
          >Send Money</v-btn
        >
      </template>
    </user-card>
  </div>
</template>

<script>
import UserCard from "@/components/UserCard.vue";
import accountService from "@/services/AccountService";
import transferService from "@/services/TransferService";
import requestService from "@/services/RequestService";

export default {
  name: "list-users",
  components: { UserCard },
  data() {
    return {
      users: [],
      transactionDTO: {
        from: "",
        to: "",
        amount: "",
      },
      transactionType: {},
      processedTransfer: false,
    };
  },
  methods: {
    listAll() {
      accountService.listUsers().then((response) => {
        this.users = response.data;
      });
    },
    sendTransfer(transferDTO) {
      transferService.postTransfer(transferDTO).then((response) => {
        if (response.status === 201) {
          this.processedTransfer = true;
          alert("Transfer sent!");
        }
      });
    },
    sendRequest(requestDTO) {
      requestService.postRequest(requestDTO).then((response) => {
        if (response.status === 201) {
          this.processedTransfer = true;
          alert("Request sent!");
        }
      });
    },
    printDTO(recipientId, transactionType) {
      this.transactionDTO.from = this.currentUserId;
      this.transactionDTO.to = recipientId;
      if (transactionType === true) {
        this.sendTransfer(this.transactionDTO);
      } else if (transactionType === false) {
        this.sendRequest(this.transactionDTO);
      }
    },
  },
  computed: {
    currentUserId() {
      return this.$store.state.user.id;
    },
  },
  created() {
    this.listAll();
  },
};
</script>

<style scoped>
</style>
        