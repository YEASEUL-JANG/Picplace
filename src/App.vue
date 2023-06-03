<template>
    <body class="d-flex flex-column">
    <main class="flex-shrink-0">
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container px-5">
                <router-link to="/" class="navbar-brand">PlacePic!</router-link>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <router-link to="/list" class="nav-link">Place</router-link>
                        </li>
                        <li class="nav-item dropdown"  v-if="currentUser && !isAdmin">
                            <a class="nav-link dropdown-toggle" id="navbarDropdownBlog" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">{{
                                    currentUser.username
                                }}</a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownBlog">
                                <li><router-link to="/placepic" class="nav-link">My PlacePic</router-link></li>
                                <li><router-link to="/profile" class="nav-link">Profile</router-link></li>
                                <li><router-link to="#" class="nav-link" @click="logOut">Logout</router-link></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown" v-if="!currentUser">
                            <a class="nav-link dropdown-toggle" id="navbarDropdownPortfolio" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Login</a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownPortfolio">
                                <li><router-link to="/register" class="nav-link">Register</router-link></li>
                                <li><router-link to="/login" class="nav-link">Login</router-link></li>
                            </ul>
                        </li>
<!--                        admin 메뉴-->
                        <li class="nav-item dropdown" v-if="isAdmin">
                            <a class="nav-link dropdown-toggle" id="navbarDropdownPortfolio" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Admin</a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownPortfolio">
                                <li><router-link to="/admin/placeList" class="nav-link">Place 목록</router-link></li>
                                <li><router-link to="/admin/placeRegister" class="nav-link">Place 등록</router-link></li>
                                <li><router-link to="#" class="nav-link" @click="logOut">Logout</router-link></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Page content-->
            <div class="container px-5">
                <router-view />
            </div>
    </main>
    </body>
    <transition name="slide">
        <Toast/>
    </transition>
</template>
<script>
import {useStore} from "vuex";
import {computed} from "vue";
import {useToast} from "@/common/toast";
import { useRouter } from "vue-router";
import Toast from "@/components/Toast.vue";

export default {
    components:{
      Toast
    },
  setup(){
      const {triggerToast} = useToast();
      const store = useStore();
      const router = useRouter();
      const currentUser = computed(() => store.getters["getcurrentUser"]);
      const isAdmin = computed(() => {
          return currentUser.value?.role === "ADMIN";
      });
      const logOut = () => {
          store.dispatch("clearUser");
          triggerToast("로그아웃 되었습니다.");
          router.push({name: "Login"});
      }
      return{
          currentUser,
          logOut,
          isAdmin
      }
  }
}
</script>

<style>
</style>
