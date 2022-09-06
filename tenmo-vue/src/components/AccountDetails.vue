<template>
	<div class="panel">
		<div class="panel-head">
			<h3>Account List</h3>
		</div>
		<div class="panel-content">
			<caption>
				Active accounts:
			</caption>
			<div id="account-wrapper">
				<table class="account-table-list">
					<thead>
						<tr class="tableColHeader">
							<th>Account No.&nbsp;</th>
							<th>User Id&nbsp;</th>
							<th>Balance&nbsp;</th>
						</tr>
					</thead>
					<tbody>
						<tr class="account-details">
							<td>{{ account.accountId }}</td>
							<td>{{ account.userId }}</td>
							<td>${{ account.balance }}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="panel-footer"></div>
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
			this.getAccount(this.userId);
		},
		methods: {
			getAccount(userId) {
				accountService.getAccountByUserId(userId).then((response) => {
					this.account = response.data;
				});
			},
		},
		computed: {
			userId() {
				return this.$store.state.user.id;
			},
		},
	};
</script>

<style scoped>
	.panel {
		border-radius: 3px;
		border-width: thin;
		border-style: solid;
	}

	.panel-head {
		border-bottom-style: solid;
		border-width: thin;
	}

	#account-wrapper {
		border-top-style: solid;
		border-width: thin;
	}
</style>
