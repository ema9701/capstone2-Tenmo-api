<template>
  <div id="transactions">
    <v-tabs bg-color="primary" id="tab-display" grow>
      <v-tab
        title="Transfers"
        @click="
          (showTransfers = !showTransfers), (showRequests = !showRequests)
        "
      ></v-tab>
      <v-tab
        title="Requests"
        @click="
          (showRequests = !showRequests), (showTransfers = !showTransfers)
        "
      ></v-tab>
    </v-tabs>
    <v-window>
      <transfer-list v-if="showTransfers" />
      <request-list v-if="showRequests" />
    </v-window>
  </div>
</template>

<script>
import TransferList from "@/components/TransferList.vue";
import RequestList from "@/components/RequestList.vue";

export default {
  name: "transaction-list-view",
  components: { TransferList, RequestList },
  data() {
    return {
      showTransfers: true,
      showRequests: false,
    };
  },
};
</script>

<style scoped>
</style>