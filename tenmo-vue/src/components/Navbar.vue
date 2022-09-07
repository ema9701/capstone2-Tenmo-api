<template>
	<v-toolbar app color="blue darken-4" dark>
		<v-btn flat
			><router-link class="rlink" v-bind:to="{ name: 'home' }"
				>Home</router-link
			></v-btn
		>
		<v-btn flat
			><router-link class="rlink" v-bind:to="{ name: 'select-users' }"
				>View Users</router-link
			></v-btn
		>
		<v-btn flat
			><router-link class="rlink" v-bind:to="{ name: 'wire-money' }"
				>Send Transfer</router-link
			></v-btn
		>
		<v-btn flat
			><router-link class="rlink" v-bind:to="{ name: 'transfer-list' }"
				>View Transfers</router-link
			></v-btn
		>
		<v-btn flat
			><router-link
				class="rlink"
				v-bind:to="{ name: 'logout' }"
				v-if="$store.state.token != ''"
				>Logout</router-link
			></v-btn
		>
		<v-btn flat
			><router-link
				class="rlink"
				v-bind:to="{ name: 'update-cred' }"
				v-if="$store.state.token != ''"
				>Change Password</router-link
			></v-btn
		>
	</v-toolbar>
</template>

<script>
	export default {
		name: "navbar-comp",
		data() {
			return {};
		},
	};
</script>

<style scoped>
	.rlink {
		text-decoration: none;
	}
</style>
