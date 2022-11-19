<template>
  <div id="authorize-btn">
    <v-btn @click="dialog = true">
      <v-dialog v-model="dialog">
        <v-card width="400">
          <v-card-title class="text-center"></v-card-title>
          <v-form></v-form>
          <v-card-actions>
            <v-row justify="space-around">
              <v-btn color="primary" @click="dialog = false">Close</v-btn>
              <v-btn>Update</v-btn>
            </v-row>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-btn>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
    };
  },
};
</script>

<style scoped>
</style>