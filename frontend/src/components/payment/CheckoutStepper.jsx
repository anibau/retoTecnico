const STEPS = ['Película', 'Dulcería', 'Pago', 'Confirmación'];

export default function CheckoutStepper({ currentStep }) {
  return (
    <div className="stepper">
      {STEPS.map((label, index) => {
        const stepNumber = index + 1;
        const isDone = stepNumber < currentStep;
        const isCurrent = stepNumber === currentStep;
        return (
          <div className="stepper-item" key={label}>
            <div className={`stepper-circle${isDone ? ' done' : ''}${isCurrent ? ' current' : ''}`}>
              {isDone ? '✓' : stepNumber}
            </div>
            <span className={isCurrent || isDone ? 'stepper-label active' : 'stepper-label'}>
              {label}
            </span>
            {stepNumber < STEPS.length && (
              <div className={`stepper-line${stepNumber < currentStep ? ' done' : ''}`} />
            )}
          </div>
        );
      })}
    </div>
  );
}
