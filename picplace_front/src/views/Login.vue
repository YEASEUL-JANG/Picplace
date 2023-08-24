<template>
  <!-- Contact form-->
    <div class="bg-light rounded-3 py-5 px-4 px-md-5 mb-5">
        <div class="text-center mb-5">
            <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-envelope"></i></div>
            <h1 class="fw-bolder">Login</h1>
            <p class="lead fw-normal text-muted mb-0">Please write your name and password.</p>
        </div>
        <div class="row gx-5 justify-content-center">
            <div class="col-lg-8 col-xl-6">
                <form id="contactForm" @submit.prevent="submitForm">
                    <!-- Name input-->
                    <div class="form-floating mb-3">
                        <input class="form-control" id="username"
                               v-model="loginuser.username"
                               data-sb-validations="required" />
                        <label for="username">Username</label>
                        <div class="invalid-feedback" data-sb-feedback="username:required">A username is required.</div>
                    </div>
                    <!-- password  input-->
                    <div class="form-floating mb-3">
                        <input class="form-control" id="password" type="password"
                               v-model="loginuser.password"
                               data-sb-validations="required,password" />
                        <label for="password">password</label>
                        <div class="invalid-feedback" data-sb-feedback="password:required">A password is required.</div>
                        <div class="invalid-feedback" data-sb-feedback="password:password">Password is not valid.</div>
                    </div>
                    <!-- Submit Button-->
                    <div class="d-grid"><button class="btn btn-primary btn-lg" id="submitButton" type="submit">Submit</button></div>
                </form>
            </div>
        </div>
    </div>
</template>
<script>
import {ref} from "vue";
import {useToast} from "@/common/toast";
import axios from "@/common/axios";
import {useRouter} from "vue-router";
import store from "@/store";

export default {
    setup(){
        const {triggerToast} = useToast();
        const router = useRouter();
        const loginuser= ref({
            username: "",
            password: ""
        })

        const submitForm = async () => {
            if(!loginuser.value.username ||
            !loginuser.value.password ) {
                triggerToast("빈칸을 모두 입력하세요.","danger");
                return;
            }
            console.log(loginuser.value);
            try {
                const res = await axios.post("api/authentication/sign-in", loginuser.value);
                console.log(res.data);
                store.dispatch("updateUser",res.data);
                triggerToast("로그인 되었습니다. 홈으로 이동합니다.");
                router.push({name: 'Home'});
            } catch (err) {
                console.log(err);
                if(err?.response?.status === 404) {
                    triggerToast("아이디나 비밀번호가 존재하지 않습니다.","danger");
                }else {
                    triggerToast("시스템 에러","danger");
                }
            }
        }

        return{
            loginuser,
            submitForm,
        }
    }
}
</script>
<style>

</style>