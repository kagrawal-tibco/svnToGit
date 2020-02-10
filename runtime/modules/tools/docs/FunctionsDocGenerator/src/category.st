<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>     $title$
</title>
$if(isProduct)$
    <style type="text/css" media="all">@import "functions.css";</style>
    <link rel="Shortcut Icon" href="icon.gif" type="image/gif" />
$else$
    <style type="text/css" media="all">@import "$pathToCatalogRoot$/../functions.css";</style>
    <link rel="Shortcut Icon" href="icon.gif" type="$pathToCatalogRoot$/../image/gif" />
$endif$
<meta http-equiv="Content-type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Language" content="en-us" />
</head>
<body class="category">

<div class="header">
<ul class="ancestors">
$if(isProduct)$
    <li class="current"><a href="index.html">$productName$</a></li>
$else$
    <li><a href="$pathToCatalogRoot$/../index.html">$productName$</a></li>
$endif$
$if(isCatalog)$
    <li class="current"><a href="$catalogFileName$">$catalogName$</a></li>
$endif$
$if(isCategory)$
    <li><a href="$pathToCatalogRoot$/$catalogFileName$">$catalogName$</a></li>
$endif$
$ancestors:{ a |
<li $if(a.current)$class="current"$endif$><a href="$a.link$">$a.name$</a></li> }$
</ul>
</div>

<div class="pageBody">

<h1 class="category">		$title$
</h1>

$if(description)$
<div class="item Description">
<h2>             Description
</h2>
<div class="content">$description$</div>
</div>
$endif$

$if(catalogs)$
<div class="item Catalogs">
<h2>        Catalogs
</h2>
<div class="content">
<table class="Parameters" cellspacing="0">
<tr><th class="Name">Name</th><th class="Description">Description</th></tr>
$catalogs:{ c |
<tr><td class="Name"><code><a href="$c.name$/$catalogFileName$">$c.name$</a></code></td><td class="Description">$c.description$</td></tr>}$
</table>
</div>
</div>
$endif$

$if(categories)$
<div class="item Subcategories">
<h2>        Categories
</h2>
<div class="content">
<table class="Parameters" cellspacing="0">
<tr><th class="Name">Name</th><th class="Description">Description</th></tr>
$categories:{ c |
<tr><td class="Name"><code><a href="$c.name$/$categorySummaryFileName$">$c.name$</a></code></td><td class="Description">$c.description$</td></tr>}$
</table>
</div>
</div>
$endif$

$if(functions)$
<div class="item Functions">
<h2>             Functions
</h2>
<div class="content">
<table class="Parameters" cellspacing="0">
<tr><th class="Name">Name</th><th class="SignatureSynopsys">Signature and Synopsis</th></tr>
$functions:{ f |
<tr><td class="Name"><code><a href="$f.name$.html">$f.name$</a></code></td><td class="SignatureSynopsys"><code class="Signature">$f.signature$</code><br /><span class="Synopsis">$f.synopsis$</span></td></tr>}$
</table>
</div>
</div>
$endif$

</div>

<div class="footer">
<p>$productName$<br />
$copyright$</p>
</div>

</body></html>
