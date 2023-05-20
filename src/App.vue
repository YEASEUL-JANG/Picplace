<template>
    <div>
        <nav class="navbar navbar-expand navbar-light bg-light">
            <a href="#" class="navbar-brand ms-1">
                <img src="./assets/logo.png" height="40" />
                PlacePic
            </a>

            <div class="navbar-nav me-auto">
                <li class="nav-item" v-if="isAdmin">
                    <router-link class="nav-link" to="/admin" active-class="active">
                        Admin
                    </router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/" active-class="active">
                        Home
                    </router-link>
                </li>
            </div>

            <div class="navbar-nav ms-auto" v-if="!currentUser">
                <li class="nav-item">
                    <router-link class="nav-link" to="/register" active-class="active">
                        Sign Up
                    </router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/login" active-class="active">
                        Sign In
                    </router-link>
                </li>
            </div>

            <div class="navbar-nav ms-auto" v-if="currentUser">
                <li class="nav-item">
                    <router-link class="nav-link" to="/profile" active-class="active">
<!--                        {{ currentUser.name }}-->
                    </router-link>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link" @click="logOut"> Sign Out </a>
                </li>
            </div>
        </nav>
        <div class="container">
            <router-view />
        </div>
    </div>
</template>
<script>
//import {useStore} from "vuex";
//import {computed} from "vue";

export default {
  setup(){
      //const store = useStore();
      //const currentUser = computed(() => store.getters["getcurrentUser"]);
      return{
          //currentUser,
      }
  }
}
</script>

<style>
</style>
