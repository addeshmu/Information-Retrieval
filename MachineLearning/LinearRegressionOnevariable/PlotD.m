%uses  GradientDescentAlgo.m and ComputeSquaredCost.m
%Finds Linear Best Fit for the data in ex1data1.txt(X-->Y)
function PlotD()
  
  data = load('ex1data1.txt');
  x = data(:,1);
  y = data(:,2);
  
  figure(1);
  plot(x,y,'bx','markersize',10);
  title('Profit Data');
  xlabel("Poulation");
  ylabel("Profits");
  X = [ones(length(data),1),data(:,1)];
  
  theta = zeros(2,1);
  Max_iterations = 1500; 
  alpha  = 0.01;
  %ComptuteSquaredCost(X,y,theta);
  [theta,JHist] = GradientDescentAlgo(X,y,theta,alpha,Max_iterations);
  
  figure(2);
  plot(JHist);
  title('Jtheta Transition');
  legend('JTheta Values');
  xlabel("Number of Iterations");
  ylabel("JTheta Value");
  print -dpng image1.png
  fprintf('Theta found by gradient descent: ');
  fprintf('%f %f \n', theta(1), theta(2));
  
  figure(1);
  hold on;
  plot(X(:,2),X*theta,'r-');
  legend('Hypothesis','BestFitTrys','FinalBestFit');
  print -dpng image2.png
  
   
  theta0_vals = linspace(-10, 10, 100);
  theta1_vals = linspace(-1, 4, 100);

  % initialize J_vals to a matrix of 0's
  J_vals = zeros(length(theta0_vals), length(theta1_vals));

  % Fill out J_vals
  for i = 1:length(theta0_vals)
      for j = 1:length(theta1_vals)
	      t = [theta0_vals(i); theta1_vals(j)];    
	      J_vals(i,j) = ComputeSquaredCost(X, y, t);
      end
  end
  % Because of the way meshgrids work in the surf command, we need to 
  % transpose J_vals before calling surf, or else the axes will be flipped
  J_vals = J_vals';
  % Surface plot
  figure(3);
  surf(theta0_vals, theta1_vals, J_vals)
  xlabel('\theta_0'); ylabel('\theta_1');
  print -dpng image3.png
  
  % Contour plot
  figure(4);
  % Plot J_vals as 15 contours spaced logarithmically between 0.01 and 100
  contour(theta0_vals, theta1_vals, J_vals, logspace(-2, 3, 20))
  xlabel('\theta_0'); ylabel('\theta_1');
  hold on;
  plot(theta(1), theta(2), 'rx', 'MarkerSize', 10, 'LineWidth', 2);
  print -dpng image4.png
end
