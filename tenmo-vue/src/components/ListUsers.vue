<template>
  <div>
    <user-card v-for="user in filteredUserList" :key="user.id" :user="user">
      <template v-slot:submitForm>
        <v-form>
          <v-alert type="error" v-if="invalidMoneyWire"
            >Invalid request</v-alert
          >
          <v-alert type="success" v-if="processedTransfer"
            >Transaction processed!</v-alert
          >
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
          @click.prevent="wireMoney(user.id, transactionType)"
          >Send Money</v-btn
        >
      </template>
    </user-card>
  </div>
</template>

<script>
import UserCard from "@/components/UserCard.vue";
import accountService from "@/services/AccountService";
import trxServices from "@/services/TransactionServices";

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
      invalidMoneyWire: false,
    };
  },
  methods: {
    listAll() {
      accountService.listUsers().then((response) => {
        this.users = response.data;
      });
    },
    sendTransfer(transferDTO) {
      transferDTO = this.transactionDTO;
      trxServices
        .postTransfer(transferDTO)
        .then((response) => {
          if (response.status === 201) {
            this.processedTransfer = true;
            this.invalidMoneyWire = false;
          }
        })
        .catch((error) => {
          const response = error.response;
          if (response.status === 400) {
            this.invalidMoneyWire = true;
            this.processedTransfer = false;
          }
        });
    },
    sendRequest(requestDTO) {
      requestDTO = this.transactionDTO;
      trxServices
        .postRequest(requestDTO)
        .then((response) => {
          if (response.status === 201) {
            this.processedTransfer = true;
            this.invalidMoneyWire = false;
          }
        })
        .catch((error) => {
          const response = error.response;
          if (response.status === 400) {
            this.invalidMoneyWire = true;
            this.processedTransfer = false;
          }
        });
    },
    wireMoney(recipientId, transactionType) {
      this.transactionDTO.from = this.currentUserId;
      this.transactionDTO.to = recipientId;
      transactionType === true
        ? this.sendTransfer(this.transactionDTO)
        : this.sendRequest(this.requestDTO);
    },
  },
  computed: {
    currentUserId() {
      return this.$store.state.user.id;
    },
    filteredUserList() {
      return this.users.filter((f) => f.id !== this.currentUserId);
    },
  },
  created() {
    this.listAll();
  },
};
</script>

<style scoped>
</style>
        