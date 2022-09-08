<template>
	<div id="select-users">
		<v-card
			class="mx-auto"
			max-width="344"
			variant="outlined"
			v-for="user in users"
			:key="user.id"
		>
			<v-card-item>
				<div>
					<div class="text-overline mb-1">TEnmo User</div>
					<div class="text-h6 mb-1">{{ user.username }}</div>
					<div class="text-caption">
						{{ user.id }}
					</div>
				</div>
			</v-card-item>
			<v-card-actions>
				<slot name="openForm"></slot>
			</v-card-actions>
		</v-card>
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

<style></style>
