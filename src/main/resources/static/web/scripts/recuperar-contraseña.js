const app = Vue.createApp({

  data() {
    return {
      hideNewPass: "password",
      hideConfirmPass: "password",
      nuevaContraseña: "",
      confirmarContraseña: "",
      error: null
    }

  },

  created() {

  },

  mounted() {

    // Loader
    $(document).ready(function preloaderSetup() {
      $(".st-perloader").fadeOut();
      $("st-perloader-in").delay(150).fadeOut("slow");
    })

  },

  methods: {


    toggleType() {
      this.hideNewPass = this.hideNewPass === "password" ? "text" : "password";
    },

    toggleType2() {
      this.hideConfirmPass = this.hideConfirmPass === "password" ? "text" : "password";
    },
    recuperarContraseña() {
      if (this.nuevaContraseña == this.confirmarContraseña)
        axios.post(`/api/pacientes/contraseña?token=${this.token}&contraseña=${this.nuevaContraseña}`)
        .then(response => {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Cuenta verificada exitosamente',
            toast: true,
            showConfirmButton: false,
            timer: 1500
          })
        })
        .catch(error => {
          this.error = error.response.data
        })


      else
        this.error = "No coinciden las contraseñas."

    }

  },

  computed: {

  },

}).mount('#app')