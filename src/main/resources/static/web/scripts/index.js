const app = Vue.createApp({
  data() {
    return {

      paciente: [],
    }
  },

  mounted() {

    //Menu toggle-button for small screens
    $(document).ready(function () {
      $(".menu-icon").on("click", function () {
        $("nav ul").toggleClass("showing");
      });
    });

  },


  created() {

    axios.get('/api/pacientes/autenticado')
      .then(data => {
        this.paciente = data.data;
      })
      .catch(error => console.warn(error.message));

  },


  methods: {

  },

  computed: {

  }

}).mount('#app')