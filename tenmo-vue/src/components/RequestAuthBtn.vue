<template>
  <div id="authorize-btn">
    <v-btn @click="dialog = true">
      Update status
      <v-dialog v-model="dialog">
        <v-card width="400">
          <v-card-title class="text-center"></v-card-title>
          <v-form>
            <slot name="statusSelect"></slot>
          </v-form>
          <v-card-actions>
            <v-row justify="space-around">
              <v-btn color="primary" @click="dialog = false">Close</v-btn>
              <slot name="update"></slot>
            </v-row>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-btn>
  </div>
</template>

<script>
export default {
  name: "update-status-btn",
  props: ["request"],
  data() {
    return {
      dialog: false,
    };
  },
};
</script>

<style scoped>
</style>