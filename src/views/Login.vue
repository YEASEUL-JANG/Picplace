<template>
  <!-- Contact form-->
    <div class="bg-light rounded-3 py-5 px-4 px-md-5 mb-5">
        <div class="text-center mb-5">
            <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-envelope"></i></div>
            <h1 class="fw-bolder">Welcome to Join</h1>
            <p class="lead fw-normal text-muted mb-0">We'd love to join you</p>
        </div>
        <div class="row gx-5 justify-content-center">
            <div class="col-lg-8 col-xl-6">
                <form id="contactForm" @submit.prevent="submitForm">
                    <!-- Name input-->
                    <div class="form-floating mb-3">
                        <input class="form-control" id="username" name="username"
                               v-model="loginuser.username"
                               placeholder="Enter your id..." data-sb-validations="required" />
                        <label for="username">Username(id)</label>
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
                    <!-- address  input-->
                    <div class="form-floating" >
                        <input class="form-control" id="address" type="text"
                               v-model="loginuser.address"
                               data-sb-validations="required" @click="execDaumPostcode"/>
                        <label for="address" >Address</label>
                        <div class="invalid-feedback" data-sb-feedback="address:required">An address is required.</div>
                    </div>
                    <div class="form-floating mb-3">
                        <input class="form-control" id="detailAddress" type="text"
                               v-model="loginuser.detailAddress" data-sb-validations="required" />
                        <label for="detailAddress">Detail Address</label>
                        <div class="invalid-feedback" data-sb-feedback="detailAddress:required">A detail address is required.</div>
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
import axios from "axios";
export default {
    setup(){
        const loginuser= ref({
            username: "",
            password: "",
            address: "",
            detailAddress: "",
            zipcode: ""

        })
        const execDaumPostcode = () => {
            new window.daum.Postcode({
                oncomplete: function(data) {
                    var addr = ''; // 주소 변수
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }
                        loginuser.value.zipcode = data.zonecode;
                        loginuser.value.address = addr;
                        // 커서를 상세주소 필드로 이동한다.
                        document.getElementById("detailAddress").focus();
                }
            }).open();
        }
        const submitForm = async () => {
            const res = await axios("")
        }

        return{
            execDaumPostcode,
            loginuser,
            submitForm,



        }
    }
}
</script>
<style>

</style>