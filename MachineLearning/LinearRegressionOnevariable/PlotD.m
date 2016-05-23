function PlotD()
data = load('ex1data1.txt');
x = data(:,1);
y = data(:,2);
figure;
plot(x,y,'bx','markersize',10);
X = [ones(length(data),1),data(:,1)];
theta = zeros(2,1);
Max_iterations = 1500; 
alpha  = 0.01;
%ComptuteSquaredCost(X,y,theta);
[theta,JHist] = GradientDescentAlgo(X,y,theta,alpha,Max_iterations);
fprintf('Theta found by gradient descent: ');
fprintf('%f %f \n', theta(1), theta(2));
end



