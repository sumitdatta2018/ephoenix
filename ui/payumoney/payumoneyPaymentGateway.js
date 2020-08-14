function submitForm() {
  var form = document.getElementById('sendParam');
  var productinfo = getParameterByName('productinfo');

  var amt = getParameterByName('amt');
  var name = getParameterByName('name');
  var mobileNo = getParameterByName('mobileNo');
  var email = getParameterByName('email');
  var bookingId = getParameterByName('bookingId');
  var salt = getParameterByName('salt');
  var key = getParameterByName('key');
  var udf1 = getParameterByName('udf1');
  var udf2 = getParameterByName('udf2');
  var udf3 = getParameterByName('udf3');
  var udf4 = getParameterByName('udf4');
  var udf5 = getParameterByName('udf5');

  document.getElementById('key').value = key;
  document.getElementById('amount').value = amt;
  document.getElementById('productinfo').value = productinfo;
  document.getElementById('amount').value = amt;
  document.getElementById('firstname').value = name;
  document.getElementById('phone').value = mobileNo;
  document.getElementById('email').value = email;
  document.getElementById('txnid').value = bookingId;
  document.getElementById('udf1').value = udf1;
  document.getElementById('udf2').value = udf2;
  document.getElementById('udf3').value = udf3;
  document.getElementById('udf4').value = udf4;
  document.getElementById('udf5').value = udf5;

  /*var string = key + '|' + bookingId + '|' + amt + '|' + productInfo + '|' + name+ '|' + email +'|||||||||||'+salt;
            var encrypttext = sha512(string);*/
  var hash = key + '|' + bookingId + '|' + amt + '|' + productinfo + '|' + name + '|' + email + '|' + udf1 + '|' + udf2 + '|' + udf3 + '|' + udf4 + '|' + udf5 + '||||||' + salt

  //var hash = getParameterByName('hash');
  document.getElementById('hash').value = sha512(hash);

  document.sendParam.submit();

}

function getParameterByName(name) {
  name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
  var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
    results = regex.exec(location.search);
  return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

submitForm();
