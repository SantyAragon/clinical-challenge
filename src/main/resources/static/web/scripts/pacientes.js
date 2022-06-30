Vue.createApp({
  data() {
    return {
        paciente: [],
        intials:"",
        email: "",
      contraseña: "",
    };
  },

  mounted() {},

  created() {
    axios
      .get("/api/pacientes/autenticado")
      .then((data) => {
        this.paciente = data.data;
        let firstNameLetter = this.paciente.firstName.charAt(0).toUpperCase()
        let secondNameLetter = this.paciente.lastName.charAt(0).toUpperCase()
        this.intials =  firstNameLetter + secondNameLetter
      })
      .catch((error) => console.warn(error.message));
  },

  methods: {
    modificarDatos() {
      if (this.contrasenia != "") {
        axios
          .patch(
            `/api/pacientes/autenticado/contraseña`, `contraseña=${this.contraseña}`)
          .then((data) => {
            Swal.fire("Contraseña modificada con éxito!", "success");
          });
      }
      if (this.email != "") {
        axios
          .patch(`/api/pacientes/autenticado/email`, `email=${this.email}`)
          .then((data) => {
            Swal.fire("E-mail modificado con éxito!", "success");
          });
      }
    },
    logout() {
      axios
        .post(
          `/api/login`)
        window.location.href = "/web/login.html"
    },
    
    pedirTurno(){
        let div_sinTurnos = document.getElementById("div_sinTurnos"),
         div_conTurnos = document.getElementById("div_conTurnos"),
         contenedorParaTurnos = document.getElementById("contenedorParaTurnos")

        div_sinTurnos.classList.remove("block")
        div_sinTurnos.classList.add("none")
        div_conTurnos.classList.remove("block")
        div_conTurnos.classList.add("none")
        contenedorParaTurnos.classList.add("block")
        contenedorParaTurnos.classList.remove("none")
    }
  },

  computed: {},

}).mount("#app");

window.addEventListener('DOMContentLoaded', event => {

  // Toggle the side navigation
  const sidebarToggle = document.body.querySelector('#sidebarToggle');
  if (sidebarToggle) {
      // Uncomment Below to persist sidebar toggle between refreshes
      // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
      //     document.body.classList.toggle('sb-sidenav-toggled');
      // }
      sidebarToggle.addEventListener('click', event => {
          event.preventDefault();
          document.body.classList.toggle('sb-sidenav-toggled');
          localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
      });
  }

});
