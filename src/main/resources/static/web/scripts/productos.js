const app = Vue.createApp({
  data() {
    return {

      gInfoRapida: false,

      datosCards: [],

      gCarritoNotif: [], // Productos que van a ir al carrito
      productoCarrito: {},
      gCarritoFavs: [],

      // 
      gNumPos: 0,

      // Notificaciones
      gTotalEnCarrito: 0, // precio total de todo lo que compra
      gCantidadNotif: 0, // cantidad de productos en el carrito

      gProductosEnStorage: [],

      gProductosFiltrados: [],
      gProductosPreFiltrados: [],


      ordenSeleccionado: "",
      nombreDelBuscador: "",
      rangoDePrecios: [],
    }
  },

  mounted() {

    // Loader
    $(document).ready(function preloaderSetup() {
      $(".st-perloader").fadeOut();
      $("st-perloader-in").delay(150).fadeOut("slow");
    })

    // ===== Scroll to Top ==== 
    $(window).scroll(function () {
      if ($(this).scrollTop() >= 1080) {        // If page is scrolled more than 50px
        $('#return-to-top').fadeIn(500);    // Fade in the arrow
      } else {
        $('#return-to-top').fadeOut(500);   // Else fade out the arrow
      }
    });

    $('#return-to-top').click(function () {      // When arrow is clicked
      $('body,html').animate({
        scrollTop: 0                       // Scroll to top of body
      }, 500);
    });


  },

  created() {
    axios.get("/api/productos/")
      .then(datos => {
        this.datosCards = datos.data;

        console.log(this.datosCards);

        // STORAGE
        this.gProductosEnStorage = JSON.parse(localStorage.getItem("carrito"));

        if (this.gProductosEnStorage) {
          this.gCarritoNotif = this.gProductosEnStorage
        }

        this.gTotalEnCarrito = this.gCarritoNotif.map(prod => prod.precio * prod.cantidad).reduce((a, b) => a + b, 0);
        this.gCantidadNotif = this.gCarritoNotif.length;

        // Productos Filtrados 
        this.gProductosFiltrados = this.datosCards;
      })
      .catch(error => console.log(error))
  },

  methods: {
    formatMoney(amount) {
      return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 2 }).format(amount);
    },

    agregarAlCarrito(producto, num) {
      let conteoNumber = 1;
      if (num) {
        let conteo = document.getElementById('total' + producto.id);
        conteoNumber = parseInt(conteo.textContent);
      }
      this.productoCarrito = this.gCarritoNotif.filter(prod => producto.id == prod.id)[0]

      if (producto.stock > 0) {

        if (this.productoCarrito != undefined) {
          this.productoCarrito.cantidad += conteoNumber;
          this.productoCarrito.stock = producto.stock -= conteoNumber;
          Swal.fire({
            title: 'Productos sumados',
            icon: 'success'
          })

        }
        else {
          this.productoCarrito = {
            id: producto.id,
            nombre: producto.nombre,
            precio: producto.precio,
            tipo: producto.tipo,
            imagen: producto.imagen,
            descripcion: producto.descripcion,
            cantidad: conteoNumber,
            stock: producto.stock -= conteoNumber
          };

          this.gCarritoNotif.push(this.productoCarrito); // agregamos la CARD al carrito

          //NOTIFICACION DE AÑADIDO AL CARRITO
          Swal.fire({
            title: 'Añadido al carrito',
            icon: 'success'
          })
        }
        // AGREGA EL NUEVO ARRAY PRODUCTOS EN CARRITO AL LOCAL STORAGE
        localStorage.setItem("carrito", JSON.stringify(this.gCarritoNotif))

        this.gTotalEnCarrito = this.gCarritoNotif.map(prod => prod.precio * prod.cantidad).reduce((a, b) => a + b, 0); // Precio total
        this.gCantidadNotif = this.gCarritoNotif.length;
      }
      else {
        Swal.fire({
          title: 'Sin stock ',
          icon: 'error'
        })
      }
    },

    removerProducto(producto) {
      this.productoCarrito = this.gCarritoNotif.filter(prod => producto.id == prod.id)[0]

      let index = this.gProductosFiltrados.findIndex(prod => prod.id == producto.id);
      // SI EL OBJETO PRODUCTO TIENE CANTIDAD MAYOR A 1, SE DECREMENTA UNO.
      if (this.productoCarrito.cantidad > 1) {
        this.productoCarrito.cantidad--
        this.productoCarrito.stock++
        this.gProductosFiltrados[index].stock++

        // this.productosEnCarrito.slice(index,this.productoCarrito)
      }
      // SINO, SE ELIMINA ESE OBJETO DEL ARRAY DE PRODUCTOS FILTRANDO LOS DISTINTOS AL SELECCIONADO
      else {
        this.gCarritoNotif = this.gCarritoNotif.filter(prod => prod.id != producto.id)
        this.productoCarrito.stock++
        Swal.fire({
          title: 'Eliminado del Carrito',
          imageUrl: 'https://cdn-icons-png.flaticon.com/512/105/105739.png',
          imageHeight: 80,
        })
      }
      // ACA SE VUELVE A CALCULAR EL TOTAL DE PRODUCTOS QUE QUEDARON EN EL CARRITO
      this.gTotalEnCarrito = this.gCarritoNotif.map(prod => prod.precio * prod.cantidad).reduce((a, b) => a + b, 0)


      // SE ACTUALIZA EL LOCAL STORAGE CON EL ARRAY MODIFICADO SI FUESE EL CASO
      this.productosEnStorage = this.productosEnCarrito
      localStorage.setItem("carrito", JSON.stringify(this.gProductosEnStorage))

      // this.gCarritoNotif = this.gCarritoNotif.filter(prod => prod.id != card.id)
      // this.gTotalEnCarrito = this.gCarritoNotif.map(prod => prod.precio).reduce((a, b) => a + b, 0); // Precio total
      this.gCantidadNotif = this.gCarritoNotif.length;
    },


    detalleRapido(card) {
      let info = this.datosCards.find(prod => prod.id == card.id);
      this.gNumPos = this.datosCards.indexOf(card);
      this.gInfoRapida = true;

      let conteo = document.querySelector(".toCartCantidad");
      conteo.textContent = 1;
    },

    closeModal() {

      this.gInfoRapida = false;
    },

    agregarFav(card) {
      let id_card = document.getElementById('card' + card.id);
      if (!id_card.classList.contains('esFav')) {
        id_card.classList.add('esFav');
        this.gCarritoFavs.push(card);
      } else {
        id_card.classList.remove('esFav');
        this.gCarritoFavs = this.gCarritoFavs.filter(prod => prod.id != card.id);
      }
    },

    sumarORestar(card, op) {
      let botonResta = document.getElementById('res' + card.id);
      let botonSuma = document.getElementById('sum' + card.id);
      let conteo = document.getElementById('total' + card.id);

      let conteoNumber = parseInt(conteo.textContent);


      if (op == 'suma') {
        if (card.stock > conteoNumber) {
          conteoNumber++
          botonResta.classList.remove('disabled')
        } else {
          window.alert("Maximo de stock disponible!")
        }
      } else if (op == 'resta') {
        if (conteoNumber > 1) {
          conteoNumber--
        } else {
          botonResta.classList.add('disabled')
          window.alert("Minimo disponible de compra!")
        }
      } else {
        console.log("nada")
      }

      conteo.innerText = conteoNumber;
    },


    preZoom(card) {
      let areaImagen = document.getElementById('prezoom' + card.id).parentElement;
      this.zoomImg(areaImagen);
    },

    zoomImg(zoomArea, escala = 3) {
      if ($(window).width() > 768) {
        zoomArea.find('.zoom_launcher').fadeOut('fast');
        const imagen = zoomArea.find('.zoom_imgOrigin');
        const urlImagen = zoomArea.find('.zoom_imgSource')[0].style.backgroundImage;
        const dimensiones = {
          imagen: { width: imagen.outerWidth(), height: imagen.outerHeight() },
          lupa: { width: imagen.outerWidth() / escala, height: imagen.outerHeight() / escala }
        };
        const estiloInicial = {
          backgroundImage: urlImagen,
          backgroundSize: parseInt(imagen.css('padding')) ? `calc(100% - (${(parseInt(imagen.css('padding')) / 2) * escala}px))` : 'contain',
          transform: `scale(${escala}) translateX(${dimensiones.imagen.width / escala}px) translateY(${dimensiones.imagen.height / escala}px)`
        };
        imagen.append(`<div class="zoom_lupa"></div>`);
        zoomArea.append(`<div class="zoom_imgAlt zoomImg"><div class="zoom"></div></div>`);
        const scaleArea = zoomArea.children('.zoom_imgAlt');
        const zoom = scaleArea.children('.zoom');
        const lupa = imagen.children('.zoom_lupa');
        scaleArea.fadeIn();
        zoom.css(estiloInicial);
        lupa.css(dimensiones.lupa);
        imagen.mousemove(function (e) {
          let movimientoLupa = { x: e.pageX, y: e.pageY };
          let centroImagen = {
            x: (imagen.offset().left + (dimensiones.imagen.width / 2)),
            y: (imagen.offset().top + (dimensiones.imagen.height / 2))
          }
          let posicionLupa = {
            x: centroImagen.x - movimientoLupa.x,
            y: centroImagen.y - movimientoLupa.y
          }
          let transformLupa = {
            x: (movimientoLupa.x - (dimensiones.lupa.width / 2)) - imagen.offset().left,
            y: (movimientoLupa.y - (dimensiones.lupa.height / 2)) - imagen.offset().top
          }
          var transformZoom = (`scale(${escala}) translateX(${posicionLupa.x}px) translateY(${posicionLupa.y}px)`);
          zoom.css('transform', transformZoom);
          lupa.css('transform', `translateX(${transformLupa.x}px) translateY(${transformLupa.y}px)`);
        });
        imagen.mouseout(function () {
          lupa.remove();
          zoomArea.find('.zoom_launcher').fadeIn('fast');
          scaleArea.fadeOut('fast', function () {
            scaleArea.remove();
          })
        })
      } else {
        console.warn('Solo disponible para PC');
      }
    }
  },

  computed: {
    ordenarProductos() {
      if (this.ordenSeleccionado == "ordenDefault") {
        this.gProductosFiltrados = this.gProductosFiltrados
      } else if (this.ordenSeleccionado == "alfabeticamenteAZ") {
        this.gProductosFiltrados = this.gProductosFiltrados.sort(function (a, b) {
          if (a.nombre < b.nombre) {
            return -1
          }
        })
      } else if (this.ordenSeleccionado == "alfabeticamenteZA") {
        this.gProductosFiltrados = this.gProductosFiltrados.sort(function (a, b) {
          if (a.nombre > b.nombre) {
            return -1;
          }
        })
      } else if (this.ordenSeleccionado == "menorPrecio") {
        this.gProductosFiltrados = this.gProductosFiltrados.sort((a, b) => a.precio - b.precio)
      } else if (this.ordenSeleccionado == "mayorPrecio") {
        this.gProductosFiltrados = this.gProductosFiltrados.sort((a, b) => b.precio - a.precio)
      }
    },

    buscadorProducto() {
      if (!this.nombreDelBuscador == "") {
        this.gProductosPreFiltrados = this.datosCards.filter(producto => producto.nombre.toUpperCase().includes(this.nombreDelBuscador.toUpperCase()))
      } else {
        this.gProductosPreFiltrados = this.datosCards
      }
      this.gProductosFiltrados = []
      if (this.rangoDePrecios.length == 0) {
        this.gProductosFiltrados = this.gProductosPreFiltrados
      } else {
        this.rangoDePrecios.forEach(rango => {
          this.gProductosPreFiltrados.forEach(prod => {
            if (rango == 500 && prod.precio < 500) {
              this.gProductosFiltrados.push(prod)
            } else if (rango == 501 && prod.precio >= 500) {
              this.gProductosFiltrados.push(prod)
            }
          })
        })
      }
    },
  }

}).mount('#app')



