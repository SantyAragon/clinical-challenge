window.addEventListener('DOMContentLoaded', event => {

    const datatablesSimple = document.getElementById('datatablesSimple');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatable = document.getElementById('datatable');
    if (datatable) {
        new simpleDatatable.DataTable(datatable);
    }
});
