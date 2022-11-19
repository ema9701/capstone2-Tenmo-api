<template>
  <v-navigation-drawer v-model="drawer" app v-if="$store.state.token != ''">
    <v-layout>
      <v-list color="transparent">
        <v-list-item prepend-icon="mdi-view-dashboard">
          <v-btn flat class="flex-start"
            ><router-link class="rlink" v-bind:to="{ name: 'home' }"
              >Home</router-link
            ></v-btn
          >
        </v-list-item>
        <v-list-item prepend-icon="mdi-account-group"
          ><v-btn flat
            ><router-link class="rlink" v-bind:to="{ name: 'select-user-view' }"
              >View Users</router-link
            ></v-btn
          ></v-list-item
        >
        <v-list-item prepend-icon="mdi-cash">
          <v-btn flat
            ><router-link
              class="rlink"
              v-bind:to="{ name: 'transactions-list-view' }"
              >View Transactions</router-link
            ></v-btn
          ></v-list-item
        >
        <v-list-item prepend-icon="mdi-refresh"
          ><v-btn flat
            ><router-link class="rlink" v-bind:to="{ name: 'update-cred' }"
              >Change Password</router-link
            ></v-btn
          ></v-list-item
        >
      </v-list>
    </v-layout>
    <template v-slot:append>
      <div class="pa-2">
        <v-btn block>
          <router-link class="rlink" v-bind:to="{ name: 'logout' }"
            >Logout</router-link
          >
        </v-btn>
      </div>
    </template>
  </v-navigation-drawer>
  <v-app-bar app>
    <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
    <v-toolbar-title>TEnmo</v-toolbar-title>
  </v-app-bar>
</template>

<script>
export default {
  name: "navbar-comp",
  data() {
    return {
      drawer: false,
    };
  },
};
</script>

<style scoped>
.rlink {
  text-decoration: none;
}
</style>
