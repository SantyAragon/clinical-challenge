Vue.createApp({

  data() {
    return {
      passwordType: "password",
      loginEmail: "",
      loginContraseña: "",
      registroNombre: "",
      registroApellido: "",
      registroDni: "",
      registroEmail: "",
      registroContraseña: "",

      recuperarEmail: "",

      pacientes: [],
    }

  },

  mounted() {


    // Loader
    $(document).ready(function preloaderSetup() {
      $(".st-perloader").fadeOut();
      $("st-perloader-in").delay(150).fadeOut("slow");
    })


    //Menu toggle-button for small screens
    $(document).ready(function () {
      $(".menu-icon").on("click", function () {
        $("nav ul").toggleClass("showing");
      });
    });
  },

  created() {

    const urlParams = new URLSearchParams(window.location.search);
    const tokenID = urlParams.get('token');

    if (tokenID != null) {

      if (tokenID.length > 0) {

        axios.patch('/api/pacientes/verificacion?token=' + tokenID)
          .then(response => {

            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Contraseña re-establecida',
              toast: true,
              showConfirmButton: false,
              timer: 1500
            })


            console.log("Token confirmado")
          })
          .catch(error => {
            Swal.fire({
              position: 'top-end',
              icon: 'error',
              title: error.data,
              toast: true,
              showConfirmButton: false,
              timer: 1500
            })

            console.log(error)

          })

      }
    }

    axios.get('/api/pacientes')
      .then(datos => {

        this.pacientes = datos.data;
        console.log("Datos obtenidos correctamente!")
      })
      .catch(function (error) {
        if (error.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          // The request was made but no response was received
          // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
          // http.ClientRequest in node.js
          console.log(error.request);
        } else {
          // Something happened in setting up the request that triggered an Error
          console.log('Error', error.message);
        }
        console.log(error.config);
      });
  },

  methods: {

    toggleType() {
      this.passwordType = this.passwordType === "password" ? "text" : "password";
    },

    toggleClass() {
      const signup = document.getElementById("sign-up"),
        signin = document.getElementById("sign-in"),
        loginin = document.getElementById("login-in"),
        loginup = document.getElementById("login-up")

      signup.addEventListener("click", () => {
        loginin.classList.remove("block")
        loginup.classList.remove("none")
        loginin.classList.add("none")
        loginup.classList.add("block")
      })
      signin.addEventListener("click", () => {
        loginin.classList.remove("none")
        loginup.classList.remove("block")
        loginin.classList.add("block")
        loginup.classList.add("none")
      })
    },

    signIn() {
      axios.post('/api/login', `email=${this.loginEmail}&password=${this.loginContraseña}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
        .then(response => {
          console.log('Logueado !!!');
          window.location.href = '/web/index.html'
        })
        .catch(function (error) {
          if (error.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // The request was made but no response was received
            // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
            // http.ClientRequest in node.js
            console.log(error.request);
          } else {
            // Something happened in setting up the request that triggered an Error
            console.log('Error', error.message);
          }
          console.log(error.config);
        });
    },

    signUp() {
      axios.post('/api/pacientes', `nombre=${this.registroNombre}&apellido=${this.registroApellido}&email=${this.registroEmail}&contraseña=${this.registroContraseña}&identificacion=${this.registroDni}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
        .then(response => {
          console.log('registered');
          this.loginContraseña = this.registroContraseña;
          this.loginEmail = this.registroEmail;
          this.signIn();

        })
    },

  },

  computed: {

  },

}).mount('#app')