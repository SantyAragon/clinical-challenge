Vue.createApp({
  data() {
    return {
      gInfoRapida: false,
      datosCards: [],
      gCarritoNotif: [], // Productos que van a ir al carrito
      gInfoObject: [],
      gTotalEnCarrito: 0,
      gCantidadNotif: 0,
    }
  },

  mounted() {


  },

  created() {
    axios.get("/api/productos/")
      .then(datos => {
        this.datosCards = datos.data;

        console.log(this.datosCards);
      })
      .catch(error => console.log(error))
  },

  methods: {

    detalleRapido(card) {
      let info = this.datosCards.find(prod => prod.id == card.id);
      gInfoObject = [];
      gInfoObject.push(card);
      console.log(gInfoObject);
      gInfoRapida = true;

      //document.querySelector(".infoRapidaModal").fadeIn().css('display', 'flex'); 
    },

    agregarFav(id) {
      let id_card = document.getElementById('card'+id);
      if(!id_card.classList.contains('esFav')){
        id_card.classList.add('esFav');
      }else {
        id_card.classList.remove('esFav');
      }
    },

    agregarAlCarrito(card) {
      if (this.gCarritoNotif.includes(card)) {
        window.alert("Ya tienes este producto en el carrito")
      }
      else {
        this.gCarritoNotif.push(card); // agregamos la CARD al carrito
        this.gTotalEnCarrito = this.gCarritoNotif.map(prod => prod.precio).reduce((a, b) => a + b, 0); // Precio total
        this.gCantidadNotif = this.gCarritoNotif.length;
        console.log(this.gCarritoNotif)
      }
    },

    removerProducto(card){
      this.gCarritoNotif = this.gCarritoNotif.filter(prod => prod.id != card.id)
      this.gTotalEnCarrito = this.gCarritoNotif.map(prod => prod.precio).reduce((a, b) => a + b, 0); // Precio total
      this.gCantidadNotif = this.gCarritoNotif.length;
    },

    toCartCantidad(boton, operacion) {
      const container = boton.parents('.component_toCartCantidad');
      const numero = container.find('.toCartCantidad');
      let tipoAnim = 'normal';
      if (operacion == 'resta') {
        if (parseInt(numero.text()) == 1) return false;
        if (parseInt(numero.text() - 1) == 1) {
          boton.addClass('disabled');
        }
        tipoAnim = 'reverse';
        numero.text(parseInt(numero.text()) - 1);
      } else if (operacion == 'suma') {
        boton.siblings('.toCartBoton.disabled').removeClass('disabled');
        numero.text(parseInt(numero.text()) + 1);
      } else {
        console.log('El tipo de operacion (suma / resta) es obligatorio');
        return false;
      }
      numero.addClass(tipoAnim == 'reverse' ? 'animacion-reverse' : 'animacion');
      numero.one('animationend', function () {
        numero.removeClass(tipoAnim == 'reverse' ? 'animacion-reverse' : 'animacion');
      });
    },
    //const { codigoProducto, descripcionLarga, existencia, imagen, nombre, precio, precioOferta } = datos;
    //document.querySelector('.infoRapidaModal').fadeIn().css('display', 'flex'); 

    closeModal(e) {
      e.target.parent().fadeOut(function () {
        e.target.remove();
      })
      gInfoRapida = false;
    },

    sumarORestar(e) {
      const boton = e.target;
      toCartCantidad(boton, boton.hasClass('mas') ? 'suma' : boton.hasClass('menos') ? 'resta' : null);
    },

    preZoom(e) {
      let areaImagen = e.target.parent();
      zoomImg(areaImagen);
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

  }

}).mount('#app')



