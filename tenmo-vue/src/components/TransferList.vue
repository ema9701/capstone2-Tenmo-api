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

<style scoped></style>

<!-- 
Transfer List structure & styles: 

HTML Path/Elements: 

<div id="quickview_new" class="quickview_new">
	<div class="leftCol">
		<account-list-v1 balance ="aggregate balnce", free-balance="", available-balance="">
			<div class="accountlist-v1">
				<ul class="ng-scope" style>
					<li class="card ng-scope">
						<div class="spendLabel headerSection spendAccountHeader" ng-class="{'spendAccountHeader' : type.key == 'spend'}">
							<a class="accountToggle fa fa-caret-down" type="button" ng-class="{'fa-caret-right' : !type.expanded, 'fa-caret-down' : type.expanded }" ng-click="updateExpanded(type.label, type.key, !type.expanded);" aria-label="collapse Spend transaction list" enter-click="" tabindex="0" style=""></a>
							<span role="link" tabindex="0" class="accountLabel uppercase ng-binding ng-scope" ui-sref="accountactivity.spend" ng-if="type.key == 'spend'" aria-label="Go to Account Activity for Spend Free Balance $2,063.82 Available balance $2,063.82" enter-click="" href="#/accountactivity/spend/">
                        Spend
                    </span>
							<span class="free-balance">
								<strong>Free balance</strong>
							</span>
							<span class="available balance">
								<strong>Available balance</strong>
							</span>
							<recent-transactions-list-v1>
								<div class="recent-transactions">
									<table>
									<thead>
										<tr>
											<th>
												<div>
													Date
												</div>
											</th>
											<th>
												<div>
												Description
												</div>
											</th>
											<th>
												<div>
													Amount
												</div>
											</th>
											<th>
												<div>
													Balance
												</div>
											</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>
												<div>
													date and status data
												</div>
											</td>
											<td>
												<div>
													description data
												</div>
											</td>
											<td>
												<div>
													amount sent data
												</div>
											</td>
											<td>
												<div>
													balance data
												</div>
											</td>
										</tr>
									</tbody>
									</table>
								</div>
							</recent-transactions-list-v1>
						</div>
					</li>
				</ul>

			</div>
		</account-list-v1>
	</div>
</div>


styles: 

    font-family: Arial, sans-serif !important;
    font-size: 14px;
    line-height: 1.42857;
    color: #333333;
    -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
    -webkit-text-size-adjust: 100%;
    -webkit-font-smoothing: antialiased;
    box-sizing: border-box;
    width: 64%;
    margin-left: 1.25%;
    margin-right: 1.5%;
    float: left;
    min-height: 100px;

-->
