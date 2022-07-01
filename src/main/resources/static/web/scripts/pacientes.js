const app = Vue.createApp({
  data() {
    return {
      paciente: [],
      intials: "",
      email: "",
      contraseña: "",

      switchNoche: 0,

      myDatePicker: "",
      fechaSeleccionada: "",
      horaSeleccionada: "",
      horarioElegido: "",

      turnos: [],
      servicios: [],
      servicioElegido: {},
      profesionalElegido: {},
      gVistaWeb: 0,
      error: '',
    };
  },

  mounted() {
    this.principal = document.getElementsByClassName("nochee")
  },

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

        this.myDatePicker = MCDatepicker.create({
          el: '#selectCalendar',
          bodyType: 'modal',
          disableWeekends: true,
          minDate: new Date(2022, 6, 1),
          selectedDate: new Date(2022, 6, 1),
        })

        this.myDatePicker.onClose(() => {
          let dia = this.myDatePicker.getDate();
          let mes = this.myDatePicker.getMonth() + 1;
          let año = this.myDatePicker.getYear();

          this.fechaSeleccionada = año + "-" + mes + "-" + dia;

          this.gVistaWeb = 3;
          console.log(this.fechaSeleccionada);
        });

      })
      .catch((error) => console.warn(error.message));

    axios.get("/api/servicios")
      .then((datos) => {
        this.servicios = datos.data.sort((a, b) => a.id - b.id);

        console.log(this.servicios);
      })
      .catch((error) => console.warn(error.message));
  },

  methods: {
    sortear(array) {
      return array.sort((a, b) => a.id - b.id);
    },
    verMisTurnos() {
      this.gVistaWeb = 0;
    },

    verProductosComprados() {
      this.gVistaWeb = 5;
    },
    verServiciosTomados() {
      this.gVistaWeb = 6;
    },

    formatFecha(fecha) {
      let date = new Date(fecha);
      result = date.toLocaleString();
      return result;
    },

    openCalendar(profesional) {

      this.profesionalElegido = {};
      this.profesionalElegido = profesional;
      console.log(this.profesionalElegido);
      // open
      this.myDatePicker.open('#selectCalendar');
    },


    formatMoney(amount) {
      return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        minimumFractionDigits: 2
      }).format(amount);
    },

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

    getFilterTurnos() {
      return this.turnos.filter(turno => this.getProximoTurno(turno.fecha));
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
      this.gVistaWeb = 1;
    },

    pedirTurnoConProfesional(servicio) {

      this.servicioElegido = {};
      this.servicioElegido = servicio;
      console.log(this.servicioElegido);
      this.gVistaWeb = 2;
    },

    generarTurno() {
      console.log(this.servicioElegido, this.profesionalElegido, this.fechaSeleccionada, this.horarioElegido)

      if (this.servicioElegido != {} && this.profesionalElegido != {} && this.fechaSeleccionada != '' && this.horarioElegido != '') {
        let servicioss = [];
        let fecha = new Date(this.fechaSeleccionada).toISOString().slice(0, 10)
        let aux = {
          idServicio: this.servicioElegido.id,
          fecha: `${fecha}T${this.horarioElegido}:00:00`,
          idProfesional: this.profesionalElegido.id,
        }

        //si hubiese más servicios, repetimos un foreach y agregamos muchos aux
        servicioss.push(aux)

        let objt = {
          servicios: servicioss,
          productos: []
        }

        axios.post('/api/facturas/create', objt)
          .then(response => {
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: "Turno solicitado exitosamente",
              toast: true,
              showConfirmButton: true,
              timer: 5500
            }).then((result) => {
              setTimeout(window.location.reload(), 5500)
              console.log(result);
              if (result.isConfirm)
                window.location.reload()
            })
          })
          .catch(error => {
            Swal.fire({
              position: 'top-end',
              icon: 'error',
              title: "Hubo un error, vuelva a intentarlo nuevamente",
              toast: true,
              showConfirmButton: false,
              timer: 1500
            })
          })
      } else {
        this.error = "Faltan datos para solicitar su turno"
      }

    },

    volverAtras() {
      if (this.gVistaWeb != 0) {
        this.gVistaWeb -= 1;
      }
    },

    logOut() {
      axios.post('/api/logout')
        .then(response => {
          window.location.href = '/web/index.html'
        })

    },
    modoNoche() {
      if (this.switchNoche) {
        Array.from(this.principal).forEach(element => element.style.color = "black");
        Array.from(this.principal).forEach(element => element.style.backgroundColor = "#414141");
      } else {
        Array.from(this.principal).forEach(element => element.style.color = "black");
        Array.from(this.principal).forEach(element => element.style.backgroundColor = "white");
      }
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