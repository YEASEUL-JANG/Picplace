import {createRouter, createWebHistory} from "vue-router";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import Restaurant from "@/views/Restaurant.vue";
import Cafe from "@/views/Cafe.vue";
import Profile from "@/views/Profile.vue";
import PlacePic from "@/views/PlacePic.vue";
import PlaceRegister from "@/views/admin/placeRegister.vue";
import PlaceList from "@/views/admin/placeList.vue";

const routes= [
    {
        name: "Home",
        path: "/",
        component: Home,
    },
    {
        name: "Restaurant",
        path: "/restaurant",
        component: Restaurant,
    },
    {
        name: "Profile",
        path: "/profile",
        component: Profile,
    },
    {
        name: "Placepic",
        path: "/placepic",
        component: PlacePic,
    },
    {
        name: "Cafe",
        path: "/cafe",
        component: Cafe,
    },
    {
        name: "Login",
        path: "/login",
        component: Login,
    },
    {
        name: "Register",
        path: "/register",
        component: Register,
    },
    {
        name: "PlaceRegister",
        path: "/admin/placeRegister",
        component: PlaceRegister,
    },
    {
        name: "PlaceList",
        path: "/admin/placeList",
        component: PlaceList,
    },
    {
        name: "PlaceEdit",
        path: "/admin/placeEdit/:placeId",
        component: Register,
    },

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;