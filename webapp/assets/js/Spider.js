(function ($)
{
	//全局系统对象
    window['SP'] = {};
    
    SP.checker=function(form){
    	form.trigger("checkInput");
    	if( $(".formErrorContent").css("display")=="block"){return false;}
    	return true;
    };
    
    SP.ajax=function(form,options,check){
    	if(check===undefined){
    		check=true;
    	}
    	var exec=true;
    	if(check){
    		check=SP.checker(form);
    		exec=check;
    	}
    	if(exec){
	    	var p = options || {};
	    	var url = p.url || form.attr("action");
	    	var sendType=form.attr("method") || p.type || 'post';
	    	var pdata=p.data || SP.serializeToJson(form);
	    	  $.ajax({
	              cache: false,
	              contentType: 'application/json',
	              async: true,
	              url: url,
	              data: pdata,
	              dataType: 'json', type: sendType,
	              beforeSend: function ()
	              {
	                  if (p.beforeSend)
	                      p.beforeSend();
	              },
	              complete: function ()
	              {
	                  if (p.complete)
	                      p.complete();
	              },
	              success: function (res){
	            	  if (p.success){
	            		  p.success(res);
	            	  }else{
	            		  if(res.result=='true'){
	            			  noty($.parseJSON('{"text":"操作成功","layout":"bottomLeft","type":"success"}'));
	            		  }else{
	            			  noty($.parseJSON('{"text":"操作失败,错误信息:'+res.result+'","layout":"bottomLeft","type":"error"}'));
	            		  }
	            	  }
	                      
	              },
	              error: function (res, b)
	              {
	            	  if(p.error){
	            		  p.error(res,b);
	            	  }else{
	            		  noty($.parseJSON('{"text":"操作失败Error,错误信息:'+res+b+'","layout":"bottomLeft","type":"error"}'));
	            	  }
	              }
	    	  });
    	}
    };
    SP.serializeToJson = function(obj) {
        var o = {};
        var a = obj.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [ o[this.name] ];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return JSON2.stringify(o);
    }
})(jQuery);