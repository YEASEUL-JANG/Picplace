<template>
    <body class="d-flex flex-column">
    <main class="flex-shrink-0">
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container px-5">
                <a class="navbar-brand" href="index.html">PlacePic!</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <router-link to="/" class="nav-link">Home</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="/restaurant" class="nav-link">Restaurant</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="/cafe" class="nav-link">Cafe</router-link></li>
                        <li class="nav-item" v-if="currentUser">
                            <router-link to="#" class="nav-link" @click="logOut">Logout</router-link></li>
                        <li class="nav-item dropdown"  v-if="currentUser">
                            <a class="nav-link dropdown-toggle" id="navbarDropdownBlog" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">My menu</a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownBlog">
                                <li><router-link to="/placepic" class="nav-link">My PlacePic</router-link></li>
                                <li><router-link to="/profile" class="nav-link">My profile</router-link></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown" v-if="!currentUser">
                            <a class="nav-link dropdown-toggle" id="navbarDropdownPortfolio" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Login</a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownPortfolio">
                                <li><router-link to="/register" class="nav-link">Register</router-link></li>
                                <li><router-link to="/login" class="nav-link">Login</router-link></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Page content-->
        <section class="py-5">
            <div class="container px-5">
                <router-view />
            </div>
        </section>
    </main>
    </body>

<!--    <div>-->
<!--        <nav class="navbar navbar-expand navbar-light bg-light">-->
<!--            <a href="#" class="navbar-brand ms-1">-->
<!--                <img src="./assets/logo.png" height="40" />-->
<!--                PlacePic-->
<!--            </a>-->

<!--            <div class="navbar-nav me-auto">-->
<!--                <li class="nav-item" v-if="isAdmin">-->
<!--                    <router-link class="nav-link" to="/admin" active-class="active">-->
<!--                        Admin-->
<!--                    </router-link>-->
<!--                </li>-->
<!--                <li class="nav-item">-->
<!--                    <router-link class="nav-link" to="/" active-class="active">-->
<!--                        Home-->
<!--                    </router-link>-->
<!--                </li>-->
<!--            </div>-->

<!--            <div class="navbar-nav ms-auto" v-if="!currentUser">-->
<!--                <li class="nav-item">-->
<!--                    <router-link class="nav-link" to="/register" active-class="active">-->
<!--                        Sign Up-->
<!--                    </router-link>-->
<!--                </li>-->
<!--                <li class="nav-item">-->
<!--                    <router-link class="nav-link" to="/login" active-class="active">-->
<!--                        Sign In-->
<!--                    </router-link>-->
<!--                </li>-->
<!--            </div>-->

<!--            <div class="navbar-nav ms-auto" v-if="currentUser">-->
<!--                <li class="nav-item">-->
<!--                    <router-link class="nav-link" to="/profile" active-class="active">-->
<!--&lt;!&ndash;                        {{ currentUser.name }}&ndash;&gt;-->
<!--                    </router-link>-->
<!--                </li>-->
<!--                <li class="nav-item">-->
<!--                    <a href="#" class="nav-link" @click="logOut"> Sign Out </a>-->
<!--                </li>-->
<!--            </div>-->
<!--        </nav>-->
<!--        <div class="container">-->
<!--            <router-view />-->
<!--        </div>-->
<!--    </div>-->
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
