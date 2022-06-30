const app = Vue.createApp({
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
      resetEmail: "",
      pacientes: [],
      carrito: "",
      carritParam: "",

    }
  },

  mounted() {
    $(window).scroll(function () {
      if ($(this).scrollTop() >= 1080) { // If page is scrolled more than 50px
        $('#return-to-top').fadeIn(500); // Fade in the arrow
      } else {
        $('#return-to-top').fadeOut(500); // Else fade out the arrow
      }
    });

    $('#return-to-top').click(function () { // When arrow is clicked
      $('body,html').animate({
        scrollTop: 0 // Scroll to top of body
      }, 500);
    });
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
    this.carritParam = urlParams.get('carrito')
    if (tokenID != null) {

      if (tokenID.length > 0) {

        axios.patch('/api/pacientes/verificacion?token=' + tokenID)
          .then(response => {

            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Cuenta verificada exitosamente',
              toast: true,
              showConfirmButton: false,
              timer: 3500
            })

          })
          .catch(error => {
            Swal.fire({
              position: 'top-end',
              icon: 'error',
              title: error.response.data,
              toast: true,
              showConfirmButton: false,
              timer: 3500
            })

            console.log(error.response.data)

          })

      }
    }
    if (this.carritParam != null) {
      if (this.carritParam == 'true') {
        this.carrito = "Para continuar la compra, debe iniciar sesion o registrarse."
      }
    }

    // axios.get('/api/pacientes')
    //     .then(datos => {

    //         this.pacientes = datos.data;
    //         console.log("Datos obtenidos correctamente!")
    //     })
    //     .catch(function (error) {
    //         if (error.response) {
    //             // The request was made and the server responded with a status code
    //             // that falls out of the range of 2xx
    //             console.log(error.response.data);
    //             console.log(error.response.status);
    //             console.log(error.response.headers);
    //         } else if (error.request) {
    //             // The request was made but no response was received
    //             // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
    //             // http.ClientRequest in node.js
    //             console.log(error.request);
    //         } else {
    //             // Something happened in setting up the request that triggered an Error
    //             console.log('Error', error.message);
    //         }
    //         console.log(error.config);
    //     });
  },

  methods: {

    toggleType() {
      this.passwordType = this.passwordType === "password" ? "text" : "password";
    },

    registerForm() {
      let loginin = document.getElementById("login-in"),
        loginup = document.getElementById("login-up")

      loginin.classList.remove("block")
      loginup.classList.remove("none")
      loginin.classList.add("none")
      loginup.classList.add("block")
    },

    loginForm() {
      let loginin = document.getElementById("login-in"),
        loginup = document.getElementById("login-up")

      loginin.classList.remove("none")
      loginup.classList.remove("block")
      loginin.classList.add("block")
      loginup.classList.add("none")
    },

    forgotPassword() {
      let loginin = document.getElementById("login-in"),
        reset = document.getElementById("reset")
      loginin.classList.remove("block")
      reset.classList.remove("none")
      loginin.classList.add("none")
      reset.classList.add("block")
    },

    backToSignIn() {
      let loginin = document.getElementById("login-in"),
        reset = document.getElementById("reset")
      loginin.classList.remove("none")
      reset.classList.remove("block")
      loginin.classList.add("block")
      reset.classList.add("none")
    },

    signIn() {
      if (this.loginEmail != "" && this.loginContraseña != "") {
        axios.post('/api/login', `email=${this.loginEmail}&password=${this.loginContraseña}`, {
            headers: {
              'content-type': 'application/x-www-form-urlencoded'
            }
          })
          .then(response => {
            axios.get('/api/autenticado')
              .then(response => {

                if (this.carritParam == 'true') {
                  window.location.href = "./carrito.html"
                } else if (response.data === 'Admin')
                  window.location.href = "./admin.html"

                else if (response.data === 'Profesional')
                  window.location.href = "./profesional.html"

                else if (response.data === 'Paciente')
                  window.location.href = './pacientesnuevo.html'

              })
          })
          .catch(error => {
            Swal.fire({
              position: 'top-end',
              icon: 'error',
              title: error.response.data.message,
              toast: true,
              showConfirmButton: false,
              timer: 1500
            })
          })
      } else {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: "Por favor complete todos los datos",
          toast: true,
          showConfirmButton: false,
          timer: 1500
        })
      }
    },

    resetPassword() {
      axios.patch('/api/pacientes/contraseña', `email=${this.resetEmail}`)
        .then(response => {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Correo de recuperacion enviado',
            toast: true,
            showConfirmButton: false,
            timer: 1500
          })
        })
        .catch(error => {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: error.response.data,
            toast: true,
            showConfirmButton: false,
            timer: 1500
          })
        })

    },

    signUp() {
      axios.post('/api/pacientes', `nombre=${this.registroNombre}&apellido=${this.registroApellido}&email=${this.registroEmail}&contraseña=${this.registroContraseña}&identificacion=${this.registroDni}`, {
          headers: {
            'content-type': 'application/x-www-form-urlencoded'
          }
        })
        .then(response => {
          console.log('registered');
          this.loginContraseña = this.registroContraseña;
          this.loginEmail = this.registroEmail;


        })

    },
  },

  computed: {

  },

}).mount('#app')