 %X = imread("durer.png");
 %X = X(:);
 %save('myDataFile.mat','X');
 %y = '1'
 %save('myDataFile.mat','y');
 %imagesc(reshape(X,[743,679,3]));

temp = imread("train/cat.0.jpg");
%temp = rgb2gray(temp);
temp = double(temp);
temp= temp(:);
X = temp';
for i =1:12499,
  path = strcat("train/cat.",int2str(i),".jpg");
  temp = imread(path);
 % temp = rgb2gray(temp);
  temp = double(temp);
  temp = temp(:);
  X = [X;temp'];
end

for i =0:12499,
  path = strcat("train/dog.",int2str(i),".jpg");
  temp = imread(path); 
%  temp = rgb2gray(temp);
  temp = double(temp);
  temp = temp(:);
  X = [X ;temp'];
end  
%imagesc(reshape(X(16001:20000),[40 100]));
%colormap(flipud(gray));  
y = ones(15000,1);
y = [y;1.+ones(15000,1)];
save('myDataFile1.mat','X','y'); 
