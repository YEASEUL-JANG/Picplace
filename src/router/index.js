import {createRouter, createWebHistory} from "vue-router";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import Restaurant from "@/views/Restaurant.vue";
import Cafe from "@/views/Cafe.vue";
import Profile from "@/views/Profile.vue";
import PlacePic from "@/views/PlacePic.vue";

const routes= [
    {
        name: "home",
        path: "/",
        component: Home,
    },
    {
        name: "restaurant",
        path: "/restaurant",
        component: Restaurant,
    },
    {
        name: "profile",
        path: "/profile",
        component: Profile,
    },
    {
        name: "placepic",
        path: "/placepic",
        component: PlacePic,
    },
    {
        name: "cafe",
        path: "/cafe",
        component: Cafe,
    },
    {
        name: "login",
        path: "/login",
        component: Login,
    },
    {
        name: "register",
        path: "/register",
        component: Register,
    },

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;