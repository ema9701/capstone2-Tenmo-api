<template>
	<div id="footer">
		<v-footer>
			<v-card
				elevation="0"
				rounded="0"
				width="100%"
				class="text-center"
				theme="dark"
			>
				<v-divider></v-divider>
				<v-card-text class="text-white">
					{{ new Date().getFullYear() }} — <strong>TEnmo</strong>
				</v-card-text>
			</v-card>
		</v-footer>
	</div>
</template>

<script>
	export default {
		setup() {
			return {};
		},
	};
</script>

<style scoped></style>
