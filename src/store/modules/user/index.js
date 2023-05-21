export default {
state: {
    currentUser: JSON.parse(localStorage.getItem("currentUser")),
},
mutations: {
    UPDATE_USER(state, user){
        state.currentUser= user;
        localStorage.setItem("currentUser",JSON.stringify(user));
    },
    CLEAR_USER(state){
        state.currentUser= null;
        localStorage.removeItem("currentUser");
    },
},
actions: {
    updateUser({commit},user) {
        commit('UPDATE_USER',user);
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
}