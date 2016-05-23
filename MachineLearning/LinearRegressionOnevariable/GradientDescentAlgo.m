function [theta,JHist] = GradientDescentAlgo(X,y,theta,alpha,iterations)
 
  m = length(y);
  JHist = zeros(iterations,1);
  for iter = 1:iterations,
    sumTheta1 = 0;
    sumTheta2 = 0;
    for i = 1:m,
      sumTheta1 = sumTheta1 + (theta(1,1)*X(i,1)+theta(2,1)*X(i,2)-y(i))*X(i,1);
      sumTheta2 = sumTheta2 + (theta(1,1)*X(i,1)+theta(2,1)*X(i,2)-y(i))*X(i,2);
    end
    theta1 = theta(1,1) - alpha*(1/m)*sumTheta1;
    theta2 = theta(2,1) - alpha*(1/m)*sumTheta2;

    theta(1,1) = theta1;
    theta(2,1) = theta2;
    JHist(iter) = ComputeSquaredCost(X,y,theta);
  end
  theta 
end 
