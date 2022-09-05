<template>
	<nav id="nav-container" class="nav-box">
		<router-link class="rlink" v-bind:to="{ name: 'home' }">Home</router-link>
		<router-link class="rlink" v-bind:to="{ name: 'select-users' }"
			>View Users</router-link
		>
		<router-link class="rlink" v-bind:to="{ name: 'wire-money' }"
			>Send Transfer</router-link
		>
		<router-link class="rlink" v-bind:to="{ name: 'transfer-list' }"
			>View Transfers</router-link
		>
		<router-link
			class="rlink"
			v-bind:to="{ name: 'logout' }"
			v-if="$store.state.token != ''"
			>Logout</router-link
		>
		<router-link
			class="rlink"
			v-bind:to="{ name: 'update-cred' }"
			v-if="$store.state.token != ''"
			>Change Password</router-link
		>
	</nav>
	<div class="view-container">
		<router-view />
	</div>
</template>

<style>
	.nav-box {
		display: flex;
		flex-direction: row;
		justify-content: space-evenly;
		border-radius: 3px;
		border-style: solid;
		border-width: 3px;
		padding: auto;
		background-color: rgb(36, 230, 236);
		border-color: teal;
	}

	.rlink {
		font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
			"Lucida Sans", Arial, sans-serif;
		font-size: 1.5em;
		margin: auto;
		text-decoration: none;
		color: rgb(4, 12, 16);
	}

	a:not(:last-child) {
		border-right-style: solid;
		border-width: thin;
		padding-left: 5%;
		padding-right: 5%;
	}

	.rlink.router-link-exact-active {
		color: orange;
	}

	.view-container {
		height: fit-content;
		display: flex;
		align-items: center;
		justify-content: center;
		position: relative;
	}
</style>
