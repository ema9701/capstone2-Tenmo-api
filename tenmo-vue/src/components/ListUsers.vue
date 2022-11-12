<template>
  <div>
    <user-card v-for="user in users" :key="user.id" :user="user">
      <template v-slot: transferOp>
        <v-form>
          <v-label v-bind="currentUserId" v-model="from"
            >From: {{ currentUserId }}</v-label
          >
          <v-label v-model="to">To: {{ user.id }}</v-label>
          <v-text-field
            type="number"
            label="Amount"
            v-model.number="amount"
          ></v-text-field>
          <v-btn color="blue-darken-1" type="submit" @click.prevent></v-btn>
        </v-form>
      </template>
    </user-card>
  </div>
</template>

<script>
import UserCard from "@/components/UserCard.vue";
import accountService from "@/services/AccountService";
export default {
  name: "list-users",
  components: { UserCard },
  data() {
    return {
      users: [],
      transferDTO: {},
    };
  },
  methods: {
    listAll() {
      accountService.listUsers().then((response) => {
        this.users = response.data;
      });
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
        