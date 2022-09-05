<template>
	<div id="select-users">
		<table id="tblUsers">
			<thead>
				<tr>
					<th>UserId</th>
					<th>Username</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="user in users" v-bind:key="user.id">
					<td>{{ user.id }}</td>
					<td>{{ user.username }}</td>
				</tr>
			</tbody>
		</table>
	</div>
</template>

<script>
	import accountService from "@/services/AccountService";

	export default {
		name: "select-users",
		data() {
			return {
				users: {},
			};
		},
		created() {
			this.list();
		},
		methods: {
			list() {
				accountService.listUsers().then((response) => {
					this.users = response.data;
				});
			},
			selectRecipient(id) {
				this.$router.push({ name: "wire-money", params: { id: id } });
			},
		},
	};
</script>

<style scoped></style>
