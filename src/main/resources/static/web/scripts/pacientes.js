Vue.createApp({
  data() {
    return {
      paciente: [],
      intials: "",
      email: "",
      contraseña: "",


      turnos: [],
      gVistaWeb: 0,
    };
  },

  mounted() { },

  created() {
    axios.get("/api/pacientes/autenticado")
      .then((datos) => {
        this.paciente = datos.data;
        this.turnos = datos.data.servicios;

        console.log(this.paciente);
        console.log(this.turnos);
        let firstNameLetter = this.paciente.nombre.charAt(0).toUpperCase()
        let secondNameLetter = this.paciente.apellido.charAt(0).toUpperCase()
        this.intials = firstNameLetter + secondNameLetter
      })
      .catch((error) => console.warn(error.message));
  },

  methods: {
    getLessDay(fecha) {
      let fechaInicio = new Date().getTime();
      let fechaFin = new Date(fecha).getTime();

      let day_as_milliseconds = 86400000;
      let diff_in_millisenconds = fechaFin - fechaInicio;;
      let diff_in_days = diff_in_millisenconds / day_as_milliseconds;

      let total = Math.round(diff_in_days);
      return total;
    },
    formatearFecha(fecha) {
      let date = new Date(fecha);
      result = date.toLocaleString();;
      return result;
    },

    getProximoTurno(fecha) {
      let fechaInicio = new Date().getTime();
      let fechaFin = new Date(fecha).getTime();

      let day_as_milliseconds = 86400000;
      let diff_in_millisenconds = fechaFin - fechaInicio;;
      let diff_in_days = diff_in_millisenconds / day_as_milliseconds;

      if (diff_in_days < 0) {
        return false;
      } else {
        return true;
      }
    },

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

    pedirTurno() {
      let div_sinTurnos = document.getElementById("div_sinTurnos"),
        div_conTurnos = document.getElementById("div_conTurnos"),
        contenedorParaTurnos = document.getElementById("contenedorParaTurnos")

      div_sinTurnos.classList.remove("block")
      div_sinTurnos.classList.add("none")
      div_conTurnos.classList.remove("block")
      div_conTurnos.classList.add("none")
      contenedorParaTurnos.classList.add("block")
      contenedorParaTurnos.classList.remove("none")

      this.gVistaWeb = 1;
    },

    volverAtras() {
      if (this.gVistaWeb != 0) {
        this.gVistaWeb -= 1;
      }
    },

    logOut(){
      axios.post('/api/logout')
      .then(response => {
        window.location.href = '/web/index.html'
      })
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
