Vue.createApp({
  data() {
    return {
      paciente: [],
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
  },

  computed: {},
}).mount("#app");

window.addEventListener("DOMContentLoaded", (event) => {
  // Toggle the side navigation
  const sidebarToggle = document.body.querySelector("#sidebarToggle");
  if (sidebarToggle) {
    // Uncomment Below to persist sidebar toggle between refreshes
    // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
    //     document.body.classList.toggle('sb-sidenav-toggled');
    // }
    sidebarToggle.addEventListener("click", (event) => {
      event.preventDefault();
      document.body.classList.toggle("sb-sidenav-toggled");
      localStorage.setItem(
        "sb|sidebar-toggle",
        document.body.classList.contains("sb-sidenav-toggled")
      );
    });
  }
});
