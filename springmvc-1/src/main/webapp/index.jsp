<html>
<body>
<h2>Hello World!</h2>

<a href="test/testView"> testView </a>
<br><br>

<a href="test/viewresolve"> viewresolve </a>
<br><br>
<form action="test/testModelAttribute" method="post">
    <input type="hidden" name="id" value="1">
    username:<input type="text"  name="username"><br>
    email:<input type="text"  name="email"><br>
    age:<input type="text"  name="age"><br>
    <input type="submit" value="Test ModelAttributes">
</form>

<br><br>

<a href="test/map">test Map</a>
<br><br>

<a href="test/model-and-view">test modelAndView</a>
<br><br>

<a href="test/cookievalue">cookie value</a>
<br><br>

<form action="test/delete/1" method="post">
    <input type="hidden" name="_method" value="DELETE">
    <input type="submit" value="Test DELETE">
</form>

<br>

<form action="test/put/1" method="post">
    <input type="hidden" name="_method" value="PUT">
    <input type="submit" value="TestPUT">
</form>
<br>
<a href="index">index</a>
<br>
<a href="test/input" > input </a>
</body>
</html>
