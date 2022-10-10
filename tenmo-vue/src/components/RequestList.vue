<template>
    <div id="request-list">
        <div class="pb-4">Processed Requests</div>
		<v-expansion-panels
			v-for="request in requests"
			:key="request.requestId"
		>
			<v-expansion-panel>
				<v-expansion-panel-title>
					<template v-slot:default="{ expanded }">
						<v-row no-gutters>
							<v-col cols="4" class="d-flex justify-start">
								Request ID No. {{ request.requestId }}
							</v-col>
							<v-col cols="8" class="text-grey">
								<v-fade-transition leave-absolute>
									<span v-if="expanded" key="0"> Request Details</span>
								</v-fade-transition>
							</v-col>
						</v-row>
					</template>
				</v-expansion-panel-title>
				<v-expansion-panel-text>
					<v-table>
						<thead>
							<tr>
								<th>Date:</th>
								<th>From account:</th>
								<th>To account:</th>
								<th>Amount:</th>
								<th>Status:</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>{{ request.requestDate }}</td>
								<td>{{ request.requester }}</td>
								<td>{{ request.grantor }}</td>
								<td>${{ request.amount }}</td>
								<td>{{ request.validate }}</td>
                        <td>{{request.status}}</td>
							</tr>
						</tbody>
					</v-table>
               <v-btn>Test</v-btn>
               
				</v-expansion-panel-text>
			</v-expansion-panel>
		</v-expansion-panels>
    </div>
</template>

<script>
import requestService from "@/services/RequestService"
export default {
    data () {
        return {
           user: {},
           requests: {},
        }
    },
    created() {
       this.listRequests(this.currentUserId); 
    },
    methods: {
       listRequests(userId) {
          requestService.listRequestsByUserId(userId).then((response) => {
             this.requests = response.data; 
          });
       }
    },
    computed: {
       currentUserId() {
          return this.$store.state.user.id;
       },
    }
}
</script>

<style>

</style>