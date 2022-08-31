<template>
	<nav>
		<router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|
		<router-link
			v-bind:to="{ name: 'select-users' }"
			v-if="$store.state.token != ''"
			>View Users</router-link
		>
		&nbsp;|
		<router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''"
			>Logout</router-link
		>&nbsp;|
		<router-link
			v-bind:to="{ name: 'update-cred' }"
			v-if="$store.state.token != ''"
			>Change Password</router-link
		>
	</nav>
	<router-view />
</template>

<style></style>
