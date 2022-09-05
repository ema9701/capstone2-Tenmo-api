<template>
	<div id="transfer-list">
		<h3>Transfers Processed:</h3>
		<table>
			<thead>
				<tr>
					<th>Transfer ID</th>
					<th>Date</th>
					<th>Sender</th>
					<th>Recipient</th>
					<th>Amount</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
				<!-- <tr>
					<td>
						<select id="statusFilter" v-model="filter.status">
							<option value="">Show All</option>
							<option value="PENDING">Pending</option>
							<option value="APPROVED">Approved</option>
						</select>
					</td>
				</tr> -->
				<tr v-for="transfer in transfers" :key="transfer.transferId">
					<td>{{ transfer.transferId }}</td>
					<td>{{ transfer.transferDate }}</td>
					<td>{{ transfer.accountFrom }}</td>
					<td>{{ transfer.accountTo }}</td>
					<td>{{ transfer.amount }}</td>
					<td>{{ transfer.status }}</td>
				</tr>
			</tbody>
		</table>
	</div>
</template>

<script>
	import transferService from "@/services/TransferService";

	export default {
		data() {
			return {
				user: {},
				transfers: [],
				filter: {},
			};
		},
		created() {
			this.listTransfers(this.currentUserId);
		},
		methods: {
			listTransfers(userId) {
				transferService.listTranxByUserId(userId).then((response) => {
					this.transfers = response.data;
				});
			},
		},
		computed: {
			currentUserId() {
				return this.$store.state.user.id;
			},
			filteredList() {
				return this.transfers.filter((f) => {
					f.transferId.includes(this.filter.transferId) &&
						f.transferDate.includes(this.filter.transferDate) &&
						f.accountFrom.includes(this.filter.accountFrom) &&
						f.accountTo.includes(this.filter.accountTo) &&
						f.amount.includes(this.filter.amount) &&
						f.status.toUpperCase().includes(this.filter.status.toUpperCase());
				});
			},
		},
	};
</script>

<style lang="scss" scoped></style>
