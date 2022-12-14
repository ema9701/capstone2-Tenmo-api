import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
import UpdateCred from "@/components/UpdateCred"
import LogoutView from "@/views/LogoutView";
import RegisterView from "@/views/RegisterView";
import SelectUserView from "@/views/SelectUserView";
import TransactionListView from "@/views/TransactionListView"
import store from "@/store/index";

const routes = [
	{
		path: "/",
		name: "home",
		component: HomeView,
		meta: {
			requiresAuth: true,
		},
	},
	{
		path: "/about",
		name: "about",
		// route level code-splitting
		// this generates a separate chunk (about.[hash].js) for this route
		// which is lazy-loaded when the route is visited.
		component: () =>
			import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
	},
	{
		path: "/login",
		name: "login",
		component: LoginView,
		meta: {
			requiresAuth: false,
		},
	},
	{
		path: '/login/update',
		name: 'update-cred',
		component: UpdateCred,
		meta: {
			requiresAuth: true,
		},
	},
	{
		path: "/logout",
		name: "logout",
		component: LogoutView,
		meta: {
			requiresAuth: true,
		},
	},
	{
		path: "/register",
		name: "register",
		component: RegisterView,
		meta: {
			requiresAuth: false,
		},
	},
	{
		path: '/select-users',
		name: 'select-user-view',
		component: SelectUserView,
		meta: {
			requiresAuth: true,
		}
	},
	{
		path: '/view-transactions',
		name: 'transactions-list-view',
		component: TransactionListView,
		meta: {
			requiresAuth: true,
		}
	},
];

const router = new createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes,
});

router.beforeEach((to, from, next) => {
	// Determine if the route requires Authentication
	const requiresAuth = to.matched.some((x) => x.meta.requiresAuth);

	// If it does and they are not logged in, send the user to "/login"
	if (requiresAuth && store.state.token === "") {
		next("/login");
	} else {
		// Else let them go to their next destination
		next();
	}
});

export default router;
