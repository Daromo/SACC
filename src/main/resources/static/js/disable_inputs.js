/**
 * Author: Rojas Morales Jose Daniel
 * Deshabilitar los inputs de acuerdo a la seleccion del elemento select
 */

$(function () {
  disableInputsClientesForm()
  disableInputsPagosForm()
  showDivReciboHonorarios()
})

function disableInputsClientesForm () {
  $('#regimenFiscalId').change(function () {
    if ($(this).val() === '601') {
      $('#divPersonasFisicas').hide("drop",{ direction: "down" }, "slow" );
      $('#divPersonasMorales').show( "drop", 1000 );
      //Limpiar inputs
      $('#nombreCliente').val('')
      $('#apPaternoCliente').val('')
      $('#apMaternoCliente').val('')
    } else{
      $('#divPersonasMorales').hide("drop",{ direction: "down" }, "slow" );
      $('#divPersonasFisicas').show( "drop", 1000 );
      //Limpiar inputs
      $('#razonSocial').val('')      
    }
  })
}

function disableInputsPagosForm () {
  $('#selectFomasPago').change(function () {
    if ($(this).val() === '03') {
      $('#divListBancos').show()
      $('#divFolioCheque').hide(1000)
      $('#inputFolioCheque').val('')
    } else if ($(this).val() === '02'){
      $('#divFolioCheque').show()
      $('#divListBancos').hide(1000)
      $('#selectBancos').val('999')
    }else{
      $('#divListBancos').hide(1000)
      $('#divFolioCheque').hide(1000)
      $('#selectBancos').val('999')
      $('#inputFolioCheque').val('')
    }
  })

  $('#')
}

function showDivReciboHonorarios () {
  $('#selectTipoHonorarios').change(function () {
    if ($(this).val() === '1') {
      $('#divReciboHonorariosButtons').show()
    }else{
      $('#divReciboHonorariosButtons').hide(1000)
    }
  })
}


