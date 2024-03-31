import {createStore} from 'vuex'
import axios from 'axios'
const store = createStore({
    state: {
        count: 0
    },
    getters: {
        getCount(state) {
            return state.count > 0 ? state.count : 'Error';
        }
    },
    mutations: {
        addCount(state,num) {
            state.count+=num;
        },
    },
    actions: {
        asyncAddCount({commit}) {
         axios.get('/api/count').then(res => {
            commit('addCount',res.data[0]);
         })
        }
    }
})
export default store;