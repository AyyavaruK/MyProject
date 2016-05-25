/* bundlua @ 2008-11-27 00:24:05 */

/* --- docknavigation.js --- */
// x_core.js
// X v3.15.1, Cross-Browser DHTML Library from Cross-Browser.com
// Copyright (c) 2002,2003,2004 Michael Foster (mike@cross-browser.com)
// This library is distributed under the terms of the LGPL (gnu.org)

// Variables:
var xVersion='3.15.1',xNN4=false,xOp7=false,xOp5or6=false,xIE4Up=false,xIE4=false,xIE5=false,xUA=navigator.userAgent.toLowerCase();
if(window.opera){
  xOp7=(xUA.indexOf('opera 7')!=-1 || xUA.indexOf('opera/7')!=-1);
  if (!xOp7) xOp5or6=(xUA.indexOf('opera 5')!=-1 || xUA.indexOf('opera/5')!=-1 || xUA.indexOf('opera 6')!=-1 || xUA.indexOf('opera/6')!=-1);
}
else if (document.all) {
  xIE4Up=xUA.indexOf('msie')!=-1 && parseInt(navigator.appVersion)>=4;
  xIE4=xUA.indexOf('msie 4')!=-1;
  xIE5=xUA.indexOf('msie 5')!=-1;
}
// Object:
function xGetElementById(e) {
  if(typeof(e)!='string') return e;
  if(document.getElementById) e=document.getElementById(e);
  else if(document.all) e=document.all[e];
  else e=null;
  return e;
}

function xDef() {
  for(var i=0; i<arguments.length; ++i){if(typeof(arguments[i])=='undefined') return false;}
  return true;
}

function xPageX(e) {
  if (!(e=xGetElementById(e))) return 0;
  var x = 0;
  while (e) {
    if (xDef(e.offsetLeft)) x += e.offsetLeft;
    e = xDef(e.offsetParent) ? e.offsetParent : null;
  }
  return x;
}
function xPageY(e) {
  if (!(e=xGetElementById(e))) return 0;
  var y = 0;
  while (e) {
    if (xDef(e.offsetTop)) y += e.offsetTop;
    e = xDef(e.offsetParent) ? e.offsetParent : null;
  }
//  if (xOp7) return y - document.body.offsetTop; // v3.14, temporary hack for opera bug 130324
  return y;
}
/* ************** End X functions ************** */



//insert toggle button as a proper link so that it's keyboard accessible
//and so that we don't need to control event bubbling on the flow div
//(which interferes with the new popup triggering scripting)
var flowtab = null;
function insertToggleButton()
{
	var flow = document.getElementById('flow');
	var tab = document.getElementById('tab');
	flowtab = document.createElement('a');
	flowtab.id = 'flowtab';
	flowtab.title = 'Hide Nav';
	flowtab.href = 'javascript:toggle()';
	flow.insertBefore(flowtab, tab);

	//auto-hide if applicable
	if(readCookie('hidenav') == 1)
	{
		hideMe();
	}
}

function readCookie(val){
	return 0;
}

/* dock navigation panel functions */
function toggle() {
	var expiry = new Date();
	expiry.setFullYear(expiry.getFullYear()+1);
	var xmt = xPageX('flow');
	var cookieString;
	if (xmt > 50) {
		hideMe();
		cookieString = "hidenav=1; expires= " + expiry.toGMTString() + "; path=/";
	}
	else {
	 	showMe();
		cookieString = "hidenav=0; expires= " + expiry.toGMTString() + "; path=/";
	}

	// If hostname ends with sitepoint.com
	if (location.hostname.lastIndexOf('sitepoint.com') == location.hostname.length - 'sitepoint.com'.length) {
		cookieString += '; domain=.sitepoint.com'; // Allow cookie on SitePoint domain
	}
	document.cookie = cookieString;
}
function hideMe(){
	var col1 = xGetElementById('col1');
	col1.style.display = "none";
	var flow = xGetElementById('flow');

	//flow.style.width = "auto";
	//modified to fix overflow in IE7
	flow.style.width = "99%";
	flow.style.margin = "0";
	flow.style.styleFloat = flow.style.cssFloat = 'left';

  var content = xGetElementById('contentArea');
  content.className += content.className == '' ? 'expanded' : ' expanded';
  	$('#tt').datagrid().width = '100%';
  	$('#budgetTable1').datagrid().width = '100%';
  	$('#budgetTable').datagrid().width = '100%';
  	$('#guideLinesTable').datagrid().width = '100%';
	if(flowtab)
	{
		flowtab.style.backgroundImage="url('view/images/show.gif')";
		flowtab.title = 'Show Nav';
	}
}

function showMe(){
	var col1 = xGetElementById('col1');
	col1.style.display = "block";
	var flow = xGetElementById('flow');
	if(document.URL.includes('meritAdministration.htm'))
		flow.style.width = "80%";
	else
		flow.style.width = "74%";
	flow.style.margin = "0";
	flow.style.styleFloat = flow.style.cssFloat = 'right';

  var content = xGetElementById('contentArea');
  content.className = content.className.replace(/(^| )expanded( |$)/, "$1");
  content.className = content.className.replace(/ $/, "");
  $('#tt').datagrid().width = '100%';
  $('#budgetTable1').datagrid().width = '100%';
  $('#budgetTable').datagrid().width = '100%';
  $('#guideLinesTable').datagrid().width = '100%';
	if(flowtab)
	{
		flowtab.style.backgroundImage = "";
		//flowtab.title = 'Hide Nav';
	}
}






//article content image processor
function articleContentImages()
{
	//get a reference to the article content div
	//and don't continue if we don't have it
	var div = document.getElementById('article_content');
	if(!div) { return; }

	//get all images which come from "sitepoint.com/graphics"
	//or "sitepointstatic.com/graphics"
	//that are not within the "boxes" div (profile picture)
	//or "featureimage" div (feature illustration)
	//or come from a known ad server
	//or have a width and height of zero (flash ad bugs)
	var imgs = div.getElementsByTagName('img');
	var egs = [];
	for(var i=0; i<imgs.length; i++)
	{
		if(/(sitepoint(static)?\.com\/graphics)/.test(imgs[i].src)
			&&
			(!(imgs[i].parentNode.parentNode.className == 'boxes'
				|| imgs[i].parentNode.className == 'featureimage'
				|| imgs[i].className.indexOf('noteicon') != -1
				|| /(fastclick\.net|doubleclick\.net|sitepointstatic\.com\/ads|sitepoint\.com\/phpAds)/.test(imgs[i].src)
				|| (imgs[i].width == 0 && imgs[i].height == 0))
				)
			)
		{
			egs[egs.length] = imgs[i];
		}
	}

	//then replace each one with a div containing a link containing the same image
	//that has beatbox class to tie in with beatbox scripting
	for(i=0; i<egs.length; i++)
	{
		var clone = egs[i].cloneNode(true);
		var link = document.createElement('a');
		link.className += (link.className == '' ? '' : ' ') + 'beatbox';
		link.setAttribute('href', egs[i].src);
		var em = document.createElement('em');
		//using innerHTML here instead of createTextNode
		//in case the text contains characters that can't be created with that method
		em.innerHTML = egs[i].getAttribute('alt') + ' (click to view image)';
		clone.setAttribute('alt', '');
		if(!document.uniqueID)
		{
			clone.style.cssFloat = 'left';
			clone.style.styleFloat = 'left';
		}
		link.appendChild(em);
		link.appendChild(clone);
		var div = document.createElement('div');
		div.style.overflow = 'hidden';
		div.appendChild(link);
		egs[i].parentNode.insertBefore(div, egs[i]);
		egs[i].parentNode.removeChild(egs[i]);


		//div.style.border = '1px solid red';
	}
}





//init the insert toggle function
insertToggleButton();

//init the article content image processor
articleContentImages();

/* --- onload.js --- */
// JavaScript Document - only for new browsers - sets the dockable nav into a usuable position

function settabber() {
	var col1 = xGetElementById('col1');	
	col1.style.marginRight = "0";
	col1.style.width = "20%";
	var flow = xGetElementById('flow');
	flow.style.backgroundPosition = "0 0";
	flow.style.cursor = "pointer";
	var tab = xGetElementById('tab');
	tab.style.margin = "0 0 0 12px";
	tab.style.cursor = "auto";
	};
//addLoadEvent(settabber);


