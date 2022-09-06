<template>
	<nav id="nav-container">
		<ul class="nav-box">
			<li>
				<router-link class="rlink" v-bind:to="{ name: 'home' }"
					>Home</router-link
				>
			</li>
			<li>
				<router-link class="rlink" v-bind:to="{ name: 'select-users' }"
					>View Users</router-link
				>
			</li>
			<li>
				<router-link class="rlink" v-bind:to="{ name: 'wire-money' }"
					>Send Transfer</router-link
				>
			</li>
			<li>
				<router-link class="rlink" v-bind:to="{ name: 'transfer-list' }"
					>View Transfers</router-link
				>
			</li>
			<li>
				<router-link
					class="rlink"
					v-bind:to="{ name: 'logout' }"
					v-if="$store.state.token != ''"
					>Logout</router-link
				>
			</li>
			<li>
				<router-link
					class="rlink"
					v-bind:to="{ name: 'update-cred' }"
					v-if="$store.state.token != ''"
					>Change Password</router-link
				>
			</li>
			<li></li>
		</ul>
	</nav>
	<div class="view-container">
		<router-view />
	</div>
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
	.nav-box {
		font-family: "Source Sans Pro", -apple-system, BlinkMacSystemFont,
			"Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif,
			"Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
		display: flex;
		flex-direction: row;
		justify-content: space-evenly;
		border-radius: 3px;
		border-style: solid;
		border-width: 3px;

		background-color: #2780e3;
		border-color: #20c997;
	}

	nav > ul > li {
		list-style: none;
	}

	.rlink {
		font-size: 1.5em;
		margin: auto;
		text-decoration: none;
		color: #fff;
		padding: 1em;
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
