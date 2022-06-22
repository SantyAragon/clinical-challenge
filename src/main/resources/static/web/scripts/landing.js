Vue.createApp({
    data() {
      return {
        client: [],

      }
    },
    created() {
  
      axios.get('/api/clients/current') // axios.get('http://localhost:8080/api/clients/current',{headers:{'accept':'application/xml'}})

    },
  
    methods: {


    },
    computed: {
  
    }
  
  }).mount('#app')
  
