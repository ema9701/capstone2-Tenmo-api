<template>
  <div class="panel">
    <v-container>
      <div class="d-flex align-center flex-column">
        <v-card width="400" title="Current Account" class="text-center">
          <v-card-text class="text-center">
            <ul class="details">
              <li>Account Number: {{ account.accountId }}</li>
              <li>User Id: {{ account.userId }}</li>
              <li>Balance: ${{ account.balance }}</li>
            </ul>
          </v-card-text>
          <v-card-actions>
            <v-btn class="align-center">Recent Transactions</v-btn>
          </v-card-actions>
        </v-card>
      </div>
    </v-container>
  </div>
</template>

<script>
import accountService from "@/services/AccountService";

export default {
  data() {
    return {
      user: {},
      account: {},
    };
  },
  created() {
    this.getAccount(this.currentUserId);
  },
  methods: {
    getAccount(userId) {
      accountService.getAccountByUserId(userId).then((response) => {
        this.account = response.data;
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

<style scoped>
.details {
  list-style: none;
}
</style>
