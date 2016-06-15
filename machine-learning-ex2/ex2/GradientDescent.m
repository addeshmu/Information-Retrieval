function [theta, J_history] = GradientDescent(X, y, theta, alpha, num_iters)
%GRADIENTDESCENTMULTI Performs gradient descent to learn theta
%   theta = GRADIENTDESCENTMULTI(x, y, theta, alpha, num_iters) updates theta by
%   taking num_iters gradient steps with learning rate alpha

% Initialize some useful values
  m = length(y); % number of training examples
  J_history = zeros(num_iters, 1);
  
    % ====================== YOUR CODE HERE ======================
    % Instructions: Perform a single gradient step on the parameter vector
    %               theta. 
    %
    % Hint: While debugging, it can be useful to print out the values
    %       of the cost function (computeCostMulti) and gradient here.
    %
  for iter = 1:num_iters,
    tempTheta = zeros(3,1);
    for i = 1:size(theta,1),
      tempTheta(i) = theta(i) - (alpha/m * sum((sigmoid(X*theta)-y).*X(:,i)));
    end
    theta = tempTheta;
    % ============================================================

    % Save the cost J in every iteration    
    [J_h,grad] = costFunction(theta,X,y);
    J_history(iter) = J_h;
end

end