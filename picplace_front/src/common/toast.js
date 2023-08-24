import {computed} from "vue";
import {useStore} from "vuex";

export const useToast = () => {
    const store = useStore();
    const toasts = computed(() => store.state.toast.toasts);
    const triggerToast = (message,type='success') => {
        store.dispatch('triggerToast',{message: message, type:type});
    }
    return {
        triggerToast,
        toasts
    }
}