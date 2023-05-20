import { createStore} from "vuex";
export default createStore({
    state: {
        currentUser: JSON.parse(localStorage.getItem("currentUser"))?null:"",
    },
    mutations: {
        UPDATE_USER(state, payload){
            state.currentUser= payload.user;
            localStorage.setItem("currentUser",JSON.stringify(payload.user));
        },
        CLEAR_USER(state){
            state.currentUser= null;
            localStorage.removeItem("currentUser");
        },
    },
    actions: {
        updateUser({commit},payload) {
            commit('UPDATE_USER',payload.user);
        },
        clearUser({commit}) {
            commit('CLEAR_USER');
        }
    },
    getters: {
        getcurrentUser(state) {
            return state.currentUser;
        }
    }
});